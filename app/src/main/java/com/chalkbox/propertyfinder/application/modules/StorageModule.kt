package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.api.firebase.storage.StorageApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun storageApi(): StorageApi = StorageApi()
}