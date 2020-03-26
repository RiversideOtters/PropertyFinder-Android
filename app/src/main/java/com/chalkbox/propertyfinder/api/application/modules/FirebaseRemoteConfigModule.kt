package com.chalkbox.propertyfinder.api.application.modules

import com.chalkbox.propertyfinder.api.application.scopes.FirebaseRemoteConfigScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.chalkbox.propertyfinder.api.firebase.remoteconfig.FirebaseRemoteConfigFetcher
import com.chalkbox.propertyfinder.api.firebase.remoteconfig.RemoteConfigFetcher
import dagger.Module
import dagger.Provides

@Module
internal class FirebaseRemoteConfigModule {
    @Provides
    @FirebaseRemoteConfigScope
    fun firebaseRemoteConfig(): FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    @Provides
    @FirebaseRemoteConfigScope
    fun firebaseRemoteConfigFetcher(firebaseRemoteConfig: FirebaseRemoteConfig): RemoteConfigFetcher = FirebaseRemoteConfigFetcher(firebaseRemoteConfig)
}