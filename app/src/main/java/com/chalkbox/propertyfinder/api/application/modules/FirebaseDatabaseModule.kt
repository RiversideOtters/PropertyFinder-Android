package com.chalkbox.propertyfinder.api.application.modules

import com.chalkbox.propertyfinder.api.application.scopes.FirebaseDatabaseScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
internal class FirebaseDatabaseModule {
    @Provides
    @FirebaseDatabaseScope
    fun firebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}