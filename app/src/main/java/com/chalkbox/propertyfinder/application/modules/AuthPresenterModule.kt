package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.application.scopes.AuthActivityScope
import com.chalkbox.propertyfinder.auth.presenter.AuthActivityPresenter
import com.chalkbox.propertyfinder.dto.managers.UserAccountManager
import dagger.Module
import dagger.Provides

@Module
class AuthPresenterModule {
    @Provides
    @AuthActivityScope
    fun authSignUpActivityPresenter(accountManager: UserAccountManager): AuthActivityPresenter =
        AuthActivityPresenter(accountManager)
}