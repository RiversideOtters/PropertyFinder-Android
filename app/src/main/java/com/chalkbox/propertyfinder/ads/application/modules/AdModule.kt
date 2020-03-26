package com.chalkbox.propertyfinder.ads.application.modules

import android.app.Application
import com.chalkbox.propertyfinder.ads.AdApi
import dagger.Module
import dagger.Provides

@Module
class AdModule {
    @Provides
    fun adManager(application: Application): AdApi {
        return AdApi(application)
    }
}