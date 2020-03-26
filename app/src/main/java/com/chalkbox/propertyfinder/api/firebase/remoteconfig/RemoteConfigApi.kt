package com.chalkbox.propertyfinder.api.firebase.remoteconfig

import io.reactivex.Observable

interface RemoteConfigApi {
    val remoteConfigData: Observable<Any>
    fun fetch(key: String)
}