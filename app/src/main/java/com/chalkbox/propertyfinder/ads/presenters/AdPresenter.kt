package com.chalkbox.propertyfinder.ads.presenters

import android.app.Application
import com.chalkbox.propertyfinder.ads.AdApi
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.feed.presenters.base.DatabaseApiPresenter
import com.chalkbox.propertyfinder.dto.pojos.Ad
import javax.inject.Inject

class AdPresenter(
    application: Application,
    identifier: String,
    sortOrder: Int,
    adapter: PresenterAdapter<Ad>
) : DatabaseApiPresenter<Ad>(identifier, sortOrder, adapter),
    PresenterAdapter.Listener<Ad> {

    @Inject
    internal lateinit var presenterApi: AdApi

    override val api: DatabaseApi<Ad>
        get() = presenterApi

    init {
        AppComponentFactory()
            .newAdPresenterComponent(application)
            .inject(this)
    }
}