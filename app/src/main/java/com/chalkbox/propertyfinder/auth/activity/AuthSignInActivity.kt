package com.chalkbox.propertyfinder.auth.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.auth.presenter.AuthActivityPresenter
import com.chalkbox.propertyfinder.auth.presenter.SignUpError
import kotlinx.android.synthetic.main.activity_auth_signin.*
import kotlinx.android.synthetic.main.progress_bar.*
import javax.inject.Inject


class AuthSignInActivity : AppCompatActivity(), AuthActivityPresenter.AuthProgressListener {
    @Inject
    internal lateinit var presenter: AuthActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_auth_signin)

        AppComponentFactory()
            .newAuthActivityComponent(application)
            .inject(this)

        presenter.bindView(auth_sign_in_view)
        presenter.listener = this

        btn_signin.setOnClickListener { signIn() }

//        btn_link_login.set
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    private fun signIn() {
        val email = input_email.text.toString()
        val password = input_password.text.toString()

        presenter.signIn(email, password)
    }

    override fun authWillStart() {
        progress_bar.visibility = View.VISIBLE
        btn_signin.isEnabled = false
    }

    override fun authFinished() {
        progress_bar.visibility = View.GONE
        btn_signin.isEnabled = true
        finish()
    }

    override fun authFailed(error: SignUpError) {
        progress_bar.visibility = View.GONE
        btn_signin.isEnabled = true

        val errorTextEdit: EditText?
        when (error) {
            SignUpError.OUTPUT_ERROR -> errorTextEdit = input_password
            SignUpError.INPUT_ERROR_EMAIL -> errorTextEdit = input_email
            SignUpError.INPUT_ERROR_PASSWORD -> errorTextEdit = input_password
            else -> errorTextEdit = null
        }

        errorTextEdit?.let {
            it.error = error.message
            it.requestFocus()
        } ?: run {
            input_email.error = null
            input_password.error = null
        }
    }
}