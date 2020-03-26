package com.chalkbox.propertyfinder.application.componentfactory

import android.app.Application
import com.chalkbox.propertyfinder.MainApplication
import com.chalkbox.propertyfinder.ads.application.components.AdPresenterComponent
import com.chalkbox.propertyfinder.ads.application.components.DaggerAdPresenterComponent
import com.chalkbox.propertyfinder.application.components.*

internal class AppComponentFactory {

    private fun getApplicationComponent(application: Application): BaseComponent {
        return (application as MainApplication).baseComponent
    }

    fun newMainActivityComponent(application: Application): MainActivityComponent {
        return DaggerMainActivityComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newAdPresenterComponent(application: Application): AdPresenterComponent {
        return DaggerAdPresenterComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newTenantPresenterComponent(application: Application): TenantPresenterComponent {
        return DaggerTenantPresenterComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }


    fun newPropertyPresenterComponent(application: Application): PropertyPresenterComponent {
        return DaggerPropertyPresenterComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newAccountActionPresenterComponent(application: Application): AccountActionPresenterComponent {
        return DaggerAccountActionPresenterComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newAuthActivityComponent(application: Application): AuthActivityComponent {
        return DaggerAuthActivityComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newPropertyListActivityComponent(application: Application): PropertyListActivityComponent {
        return DaggerPropertyListActivityComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newPropertyDetailsActivityComponent(application: Application): PropertyDetailsActivityComponent {
        return DaggerPropertyDetailsActivityComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newFeedPresenterComponent(application: Application): FeedPresenterComponent {
        return DaggerFeedPresenterComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }

    fun newCreatePropertyComponent(application: Application): CreatePropertyComponent {
        return DaggerCreatePropertyComponent.builder()
            .baseComponent(getApplicationComponent(application))
            .build()
    }
}