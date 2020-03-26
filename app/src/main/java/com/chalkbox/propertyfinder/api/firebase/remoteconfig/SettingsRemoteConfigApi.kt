package com.chalkbox.propertyfinder.api.firebase.remoteconfig

import com.chalkbox.propertyfinder.api.application.componentfactory.FirebaseComponentFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class SettingsRemoteConfigApi @Inject constructor() : RemoteConfigApi {
    @Inject
    internal lateinit var remoteConfigFetcher: RemoteConfigFetcher

    private val bannerItemSubject = BehaviorSubject.create<Any>()

    override val remoteConfigData: Observable<Any> = bannerItemSubject

    init {
        FirebaseComponentFactory().newFirebaseRemoteConfigComponent().inject(this)
    }

    override fun fetch(key: String) {
        remoteConfigFetcher.fetch(key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { data -> bannerItemSubject.onNext(data) }
            .doOnError {
                // TODO: LOG ERROR
            }
            .onErrorResumeNext(Observable.empty())
            .subscribe()
    }
}
