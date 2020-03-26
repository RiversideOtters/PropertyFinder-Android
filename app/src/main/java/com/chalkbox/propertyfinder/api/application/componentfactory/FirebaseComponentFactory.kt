package com.chalkbox.propertyfinder.api.application.componentfactory

import com.chalkbox.propertyfinder.api.application.components.*

internal class FirebaseComponentFactory {
    fun newFirebaseRemoteConfigComponent(): FirebaseRemoteConfigComponent {
        return DaggerFirebaseRemoteConfigComponent.builder()
                .build()
    }

    fun newFirebaseStorageComponent(): FirebaseStorageComponent {
        return DaggerFirebaseStorageComponent.builder()
            .build()
    }

    fun newFirebaseDatabaseComponent(): FirebaseDatabaseComponent {
        return DaggerFirebaseDatabaseComponent.builder()
            .build()
    }

    fun newFirebaseAuthComponent(): FirebaseAuthComponent {
        return DaggerFirebaseAuthComponent.builder()
            .build()
    }
}