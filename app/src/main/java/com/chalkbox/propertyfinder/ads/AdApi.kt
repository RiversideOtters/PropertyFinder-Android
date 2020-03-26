package com.chalkbox.propertyfinder.ads

import android.app.Application
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.dto.pojos.Ad
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AdApi(private val application: Application) : DatabaseApi<Ad> {

    private val adListSubject = BehaviorSubject.create<List<Ad>>()

    override val observable: Observable<List<Ad>>
        get() = adListSubject

    val ads = arrayListOf<Ad>()

    override fun get() {
        val adLoader = AdLoader.Builder(application, "ca-app-pub-3611518346729955/2184076334")
            .forUnifiedNativeAd { unifiedNativeAd: UnifiedNativeAd ->
                ads.add(Ad(unifiedNativeAd))
                adListSubject.onNext(ads)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder().build()
            )
            .build()

        adLoader.loadAds(AdRequest.Builder().build(), AD_COUNT)
    }

    companion object {
        private const val AD_COUNT = 3
    }
}