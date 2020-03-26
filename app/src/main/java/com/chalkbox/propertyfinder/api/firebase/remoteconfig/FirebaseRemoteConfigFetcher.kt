package com.chalkbox.propertyfinder.api.firebase.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import io.reactivex.Observable
import javax.inject.Inject

internal class FirebaseRemoteConfigFetcher @Inject constructor(val firebaseRemoteConfig: FirebaseRemoteConfig) :
    RemoteConfigFetcher {
    private var configSettings: FirebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
            .build()

    private val cacheExpiration: Long
        get() = 0

    init {
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
    }

    override fun fetch(key: String): Observable<RemoteConfigData> {
        return Observable.create { emitter ->
            firebaseRemoteConfig.fetch(cacheExpiration)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseRemoteConfig.activate()

                            firebaseRemoteConfig.getString(key).let { jsonString ->
                                if (key.isNotEmpty() && jsonString.isNotEmpty()) {
                                    emitter.onNext(RemoteConfigData(key, jsonString))
                                }
                            }
                        }
                    }
        }
    }
}
