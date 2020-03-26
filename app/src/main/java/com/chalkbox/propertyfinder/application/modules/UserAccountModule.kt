package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.dto.managers.FirebaseAuthAccountManager
import com.chalkbox.propertyfinder.dto.managers.UserAccountManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserAccountModule {
    @Provides
    @Singleton
    fun userAccountManager(firebaseAuthManager: FirebaseAuthAccountManager): UserAccountManager =
        UserAccountManager(firebaseAuthManager)

    @Provides
    @Singleton
    fun firebaseAuthManager(): FirebaseAuthAccountManager = FirebaseAuthAccountManager()
}
