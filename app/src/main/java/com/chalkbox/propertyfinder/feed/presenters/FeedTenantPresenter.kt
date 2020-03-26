package com.chalkbox.propertyfinder.feed.presenters

import android.app.Application
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.api.firebase.database.TenantApi
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.dto.pojos.Tenant
import com.chalkbox.propertyfinder.feed.presenters.base.DatabaseApiPresenter
import javax.inject.Inject

class FeedTenantPresenter(
    private val application: Application,
    identifier: String,
    sortOrder: Int,
    adapter: PresenterAdapter<Tenant>
) : DatabaseApiPresenter<Tenant>(identifier, sortOrder, adapter) {
    @Inject
    internal lateinit var presenterApi: TenantApi

    override val heading: String =
        application.resources.getString(R.string.feed_tenant_heading)
    override val subHeading: String =
        application.resources.getString(R.string.feed_tenant_subheading)
    override val showAllTitle: String =
        application.resources.getString(R.string.feed_tenant_show_all_button)

    override val api: DatabaseApi<Tenant>
        get() = presenterApi

    init {
        AppComponentFactory()
            .newTenantPresenterComponent(application)
            .inject(this)
    }
}