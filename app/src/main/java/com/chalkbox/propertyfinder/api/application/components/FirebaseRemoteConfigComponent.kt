package com.chalkbox.propertyfinder.api.application.components

import com.chalkbox.propertyfinder.api.application.modules.FirebaseRemoteConfigModule
import com.chalkbox.propertyfinder.api.application.scopes.FirebaseRemoteConfigScope
import com.chalkbox.propertyfinder.api.firebase.remoteconfig.SettingsRemoteConfigApi
import dagger.Component

@FirebaseRemoteConfigScope
@Component(modules = [FirebaseRemoteConfigModule::class])
interface FirebaseRemoteConfigComponent {
    fun inject(arg: SettingsRemoteConfigApi)
}
