package com.chalkbox.propertyfinder.api.application.components

import com.chalkbox.propertyfinder.api.application.modules.FirebaseStorageModule
import com.chalkbox.propertyfinder.api.application.scopes.FirebaseStorageScope
import com.chalkbox.propertyfinder.api.firebase.storage.StorageApi
import dagger.Component

@FirebaseStorageScope
@Component(modules = [FirebaseStorageModule::class])
interface FirebaseStorageComponent {
    fun inject(arg: StorageApi)
}
