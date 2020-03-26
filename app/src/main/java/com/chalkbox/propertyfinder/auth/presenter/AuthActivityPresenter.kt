package com.chalkbox.propertyfinder.auth.presenter

import android.view.View
import com.chalkbox.propertyfinder.base.presenters.Presenter
import com.chalkbox.propertyfinder.dto.managers.ApiResult
import com.chalkbox.propertyfinder.dto.managers.UserAccountManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

enum class SignUpError {
    ERROR_NONE {
        override var message: String = ""
    },
    INPUT_ERROR_NAME {
        override var message: String = "at least 3 characters"
    },
    INPUT_ERROR_EMAIL {
        override var message: String = "enter radio_button_pressed valid email address"
    },
    INPUT_ERROR_PASSWORD {
        override var message: String = "between 4 and 10 alphanumeric characters"
    },
    OUTPUT_ERROR {
        override var message: String = "An error occurred while signing up."
    }, ;

    abstract var message: String
}

class AuthActivityPresenter @Inject constructor(private val userAccountManager: UserAccountManager) :
    Presenter<View>() {
    override val resId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    private val compositeDisposable = CompositeDisposable()
    var listener: AuthProgressListener? = null

    override fun unbindView() {
        super.unbindView()
        compositeDisposable.clear()
    }

    fun signIn(email: String, password: String) {
        val error = validate(email = email, password = password)
        if (error != SignUpError.ERROR_NONE) {
            listener?.authFailed(error)
            return
        }

        listener?.authWillStart()

        val disposable = userAccountManager.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                onAuthError(it)
            }
            .onErrorResumeNext(Observable.empty())
            .subscribe(onUserAccountSignIn)
        compositeDisposable.add(disposable)
    }

    fun signUp(name: String, email: String, password: String) {
        val error = validate(name, email, password)
        if (error != SignUpError.ERROR_NONE) {
            listener?.authFailed(error)
            return
        }

        listener?.authWillStart()
        val disposable1 = userAccountManager.signUp(name, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                onAuthError(it)
            }
            .onErrorResumeNext(Observable.empty())
            .subscribe(onUserAccountSignIn)

        compositeDisposable.addAll(disposable1)
    }

    private fun onAuthError(error: Throwable) {
        SignUpError.OUTPUT_ERROR.message = error.localizedMessage
        listener?.authFailed(SignUpError.OUTPUT_ERROR)
    }

    private val onUserAccountSignIn = Consumer<ApiResult> { result ->
        if (result.success) {
            listener?.authFinished()
        } else {
            listener?.authFailed(SignUpError.OUTPUT_ERROR)
        }
    }

    private fun validate(
        name: String = VALIDATE_SIGN_IN_NAME,
        email: String,
        password: String
    ): SignUpError {
        return if (name.isEmpty() || name.length < 3) {
            SignUpError.INPUT_ERROR_NAME
        } else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SignUpError.INPUT_ERROR_EMAIL
        } else if (password.isEmpty() || password.length < 4 || password.length > 10) {
            SignUpError.INPUT_ERROR_PASSWORD
        } else {
            SignUpError.ERROR_NONE
        }
    }

    interface AuthProgressListener {
        fun authWillStart()
        fun authFinished()
        fun authFailed(error: SignUpError)
    }

    companion object {
        private const val VALIDATE_SIGN_IN_NAME = "SIGN_IN"
    }
}