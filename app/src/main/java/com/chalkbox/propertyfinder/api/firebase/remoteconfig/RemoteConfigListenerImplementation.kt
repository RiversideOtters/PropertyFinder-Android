package com.chalkbox.propertyfinder.api.firebase.remoteconfig

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RemoteConfigListenerImplementation @Inject constructor(remoteConfigApi: RemoteConfigApi) {

    private val compositeDisposable = CompositeDisposable()
    private var remoteConfigData: Any? = null

    init {
        fetch(remoteConfigApi)
    }

    private fun fetch(remoteConfigApi: RemoteConfigApi) {
        val disposable = remoteConfigApi.remoteConfigData
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                remoteConfigData = it
            }
        compositeDisposable.add(disposable)

        remoteConfigApi.fetch(SETTINGS_KEY)
    }

    companion object {
        private val SETTINGS_KEY = "settings"
    }
}