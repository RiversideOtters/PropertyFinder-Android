package com.chalkbox.propertyfinder.api.application.components

import com.chalkbox.propertyfinder.api.application.modules.FirebaseAuthModule
import com.chalkbox.propertyfinder.api.application.scopes.FirebaseAuthScope
import com.chalkbox.propertyfinder.dto.managers.FirebaseAuthAccountManager
import dagger.Component

@FirebaseAuthScope
@Component(modules = [FirebaseAuthModule::class])
interface FirebaseAuthComponent {
    fun inject(arg: FirebaseAuthAccountManager)
}
