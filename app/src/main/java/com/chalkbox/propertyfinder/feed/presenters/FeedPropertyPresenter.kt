package com.chalkbox.propertyfinder.feed.presenters

import android.app.Application
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.feed.presenters.base.DatabaseApiPresenter
import com.chalkbox.propertyfinder.intents.IntentFactory
import com.chalkbox.propertyfinder.property.details.PropertyDetailsActivity
import com.chalkbox.propertyfinder.property.details.PropertyHolder
import com.chalkbox.propertyfinder.property.list.PropertyListActivity
import javax.inject.Inject


class FeedPropertyPresenter(
    application: Application,
    identifier: String,
    sortOrder: Int,
    adapter: PresenterAdapter<Property>
) : DatabaseApiPresenter<Property>(identifier, sortOrder, adapter) {

    @Inject
    internal lateinit var presenterApi: PropertyApi

    @Inject
    lateinit var propertyHolder: PropertyHolder

    override val heading: String =
        application.resources.getString(R.string.feed_properties_heading)
    override val subHeading: String =
        application.resources.getString(R.string.feed_properties_subheading)
    override val showAllTitle: String =
        application.resources.getString(R.string.feed_properties_show_all_button)

    override
    val api: DatabaseApi<Property>
        get() = presenterApi

    init {
        AppComponentFactory()
            .newPropertyPresenterComponent(application)
            .inject(this)
    }

    override fun onItemClick(item: Property?) {
        super.onItemClick(item)

        item?.let { property ->
            propertyHolder.property = property
            navigator?.navigateTo(
                PropertyDetailsActivity::class.java
            )
        }
    }

    override fun onShowAllClick() {
        navigator?.navigateTo(PropertyListActivity::class.java)
    }
}