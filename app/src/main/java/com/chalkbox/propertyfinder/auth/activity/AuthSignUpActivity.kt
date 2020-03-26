package com.chalkbox.propertyfinder.auth.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.auth.presenter.AuthActivityPresenter
import com.chalkbox.propertyfinder.auth.presenter.SignUpError
import kotlinx.android.synthetic.main.activity_auth_signup.*
import kotlinx.android.synthetic.main.progress_bar.*
import javax.inject.Inject


class AuthSignUpActivity : AppCompatActivity(), AuthActivityPresenter.AuthProgressListener {
    @Inject
    internal lateinit var presenter: AuthActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth_signup)

        AppComponentFactory()
            .newAuthActivityComponent(application)
            .inject(this)

        presenter.bindView(auth_sign_up_view)
        presenter.listener = this

        btn_signup.setOnClickListener { signUp() }


    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    private fun signUp() {
        val name = input_name.text.toString()
        val email = input_email.text.toString()
        val password = input_password.text.toString()

        presenter.signUp(name, email, password)
    }

    override fun authWillStart() {
        progress_bar.visibility = VISIBLE
        btn_signup.isEnabled = false
    }

    override fun authFinished() {
        progress_bar.visibility = GONE
        btn_signup.isEnabled = true
        finish()
    }

    override fun authFailed(error: SignUpError) {
        progress_bar.visibility = GONE
        btn_signup.isEnabled = true

        val errorTextEdit: EditText?
        when (error) {
            SignUpError.OUTPUT_ERROR -> errorTextEdit = input_password
            SignUpError.INPUT_ERROR_NAME -> errorTextEdit = input_name
            SignUpError.INPUT_ERROR_EMAIL -> errorTextEdit = input_email
            SignUpError.INPUT_ERROR_PASSWORD -> errorTextEdit = input_password
            else -> errorTextEdit = null
        }

        errorTextEdit?.let {
            it.error = error.message
            it.requestFocus()
        } ?: run {
            input_name.error = null
            input_email.error = null
            input_password.error = null
        }
    }
}
