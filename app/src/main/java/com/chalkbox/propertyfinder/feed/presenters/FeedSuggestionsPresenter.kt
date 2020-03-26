package com.chalkbox.propertyfinder.feed.presenters

import android.app.Application
import android.content.Intent
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.api.firebase.filters.PropertyFilter
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.dto.pojos.Suggestions
import com.chalkbox.propertyfinder.feed.presenters.base.DatabaseApiPresenter
import com.chalkbox.propertyfinder.property.create.PropertyCreateImageAndCountryActivity
import com.chalkbox.propertyfinder.property.list.PropertyListActivity
import com.google.firebase.firestore.FieldPath
import javax.inject.Inject

class FeedSuggestionsPresenter(
    private val application: Application,
    identifier: String,
    sortOrder: Int,
    adapter: PresenterAdapter<Suggestions>
) : DatabaseApiPresenter<Suggestions>(identifier, sortOrder, adapter) {

    @Inject
    lateinit var propertyFilter: PropertyFilter

    override val heading: String =
        application.resources.getString(R.string.feed_suggestions_heading)

    override val api: DatabaseApi<Suggestions>? = null

    init {
        AppComponentFactory()
            .newFeedPresenterComponent(application)
            .inject(this)

        adapter.updateList(
            listOf(
                Suggestions(
                    application.resources.getString(R.string.feed_suggestions_post_room),
                    ""
                ),
                Suggestions(
                    application.resources.getString(R.string.feed_suggestions_post_profile),
                    ""
                ),
                Suggestions(
                    application.resources.getString(R.string.feed_suggestions_room),
                    "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1051&q=80"
                ),
                Suggestions(
                    application.resources.getString(R.string.feed_suggestions_whole_unit),
                    "https://images.all-free-download.com/images/graphiclarge/fine_home_interior_picture_4_167627.jpg"
                ),
                Suggestions(
                    application.resources.getString(R.string.feed_suggestions_tenant),
                    "https://www.lifeofpix.com/wp-content/uploads/2018/04/275-ake6418-jj-jite-001-1600x1000.jpg"
                )
            )
        )
    }

    override fun onItemClick(item: Suggestions?) {
        super.onItemClick(item)

        item?.let {
            when {
                it.title == application.resources.getString(R.string.feed_suggestions_room) -> {
                    propertyFilter.clear()
                    propertyFilter.addFilter(FieldPath.of("rentalType", "name", "en_us"), "room")
                    navigator?.navigateTo(PropertyListActivity::class.java)
                }
                it.title == application.resources.getString(R.string.feed_suggestions_whole_unit) -> {
                    propertyFilter.clear()
                    propertyFilter.addFilter(
                        FieldPath.of("rentalType", "name", "en_us"),
                        "whole unit"
                    )
                    navigator?.navigateTo(PropertyListActivity::class.java)
                }
                it.title == application.resources.getString(R.string.feed_suggestions_post_room) -> {
                    navigator?.navigateTo(
                        target = PropertyCreateImageAndCountryActivity::class.java,
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                }
                else -> {

                }
            }
        }
    }
}