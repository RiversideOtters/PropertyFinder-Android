package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.modules.AuthPresenterModule
import com.chalkbox.propertyfinder.application.scopes.AuthActivityScope
import com.chalkbox.propertyfinder.auth.activity.AuthSignInActivity
import com.chalkbox.propertyfinder.auth.activity.AuthSignUpActivity
import dagger.Component

@AuthActivityScope
@Component(
    dependencies = [
        BaseComponent::class
    ],
    modules = [
        AuthPresenterModule::class
    ]
)
interface AuthActivityComponent {
    fun inject(arg: AuthSignUpActivity)
    fun inject(arg: AuthSignInActivity)
}