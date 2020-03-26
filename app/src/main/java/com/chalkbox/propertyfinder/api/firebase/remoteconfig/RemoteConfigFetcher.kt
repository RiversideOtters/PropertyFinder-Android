package com.chalkbox.propertyfinder.api.firebase.remoteconfig

import io.reactivex.Observable

data class RemoteConfigData(val key: String = "",
                            val jsonString: String = "")

interface RemoteConfigFetcher {
    /**
     * Initiate fetching from the data provider, i.e. Firebase Remote Config
     *
     * @param key of the remote config data that will be fetched
     * @return Observable RemoteConfigData
     */
    fun fetch(key: String): Observable<RemoteConfigData>
}