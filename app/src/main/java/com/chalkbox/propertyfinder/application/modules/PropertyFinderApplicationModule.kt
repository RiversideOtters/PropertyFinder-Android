package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.api.firebase.remoteconfig.RemoteConfigApi
import com.chalkbox.propertyfinder.api.firebase.remoteconfig.RemoteConfigListenerImplementation
import com.chalkbox.propertyfinder.api.firebase.remoteconfig.SettingsRemoteConfigApi
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PropertyFinderApplicationModule {
    @Provides
    @Singleton
    fun remoteConfigListenerImplementation(remoteConfigApi: RemoteConfigApi): RemoteConfigListenerImplementation =
        RemoteConfigListenerImplementation(remoteConfigApi)

    @Provides
    @Singleton
    fun remoteConfigApi(): RemoteConfigApi = SettingsRemoteConfigApi()
}