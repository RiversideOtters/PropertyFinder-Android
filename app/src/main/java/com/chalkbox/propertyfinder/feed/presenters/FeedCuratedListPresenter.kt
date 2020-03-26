package com.chalkbox.propertyfinder.feed.presenters

import android.app.Application
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.feed.presenters.base.DatabaseApiPresenter

class FeedCuratedListPresenter(
    application: Application,
    identifier: String,
    sortOrder: Int,
    adapter: PresenterAdapter<Property>
) : DatabaseApiPresenter<Property>(identifier, sortOrder, adapter) {
    override val api: DatabaseApi<Property>? = null

    init {
        adapter.updateList(listOf(
            Property(name = "")
        ))
    }
}