package com.chalkbox.propertyfinder

import android.app.Application
import com.chalkbox.propertyfinder.api.firebase.remoteconfig.RemoteConfigListenerImplementation
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.application.components.BaseComponent
import com.chalkbox.propertyfinder.application.components.DaggerBaseComponent
import com.chalkbox.propertyfinder.application.providers.ApplicationComponentProvider
import javax.inject.Inject

class MainApplication : Application(), ApplicationComponentProvider {
    @Inject
    internal lateinit var remoteConfigListenerImplementation: RemoteConfigListenerImplementation

    override lateinit var baseComponent: BaseComponent

    private fun setApplicationComponent(): BaseComponent {
        if (!::baseComponent.isInitialized) {
            baseComponent = DaggerBaseComponent.builder()
                .application(this)
                .build()
        }
        return baseComponent
    }

    override fun onCreate() {
        super.onCreate()

        setApplicationComponent()
    }
}
