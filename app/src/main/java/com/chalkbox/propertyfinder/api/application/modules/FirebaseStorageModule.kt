package com.chalkbox.propertyfinder.api.application.modules

import com.chalkbox.propertyfinder.api.application.scopes.FirebaseStorageScope
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module
internal class FirebaseStorageModule {
    @Provides
    @FirebaseStorageScope
    fun firebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}