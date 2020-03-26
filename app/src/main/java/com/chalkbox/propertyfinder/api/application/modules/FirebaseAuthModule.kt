package com.chalkbox.propertyfinder.api.application.modules

import com.chalkbox.propertyfinder.api.application.scopes.FirebaseAuthScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import dagger.Module
import dagger.Provides

@Module
internal class FirebaseAuthModule {
    @Provides
    @FirebaseAuthScope
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @FirebaseAuthScope
    fun firebaseDynamicLinks(): FirebaseDynamicLinks = FirebaseDynamicLinks.getInstance()
}
