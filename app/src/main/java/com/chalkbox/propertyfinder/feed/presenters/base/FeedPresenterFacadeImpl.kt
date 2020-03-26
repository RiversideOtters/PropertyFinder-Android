package com.chalkbox.propertyfinder.feed.presenters.base

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chalkbox.propertyfinder.ads.adapters.AdPresenterAdapter
import com.chalkbox.propertyfinder.ads.presenters.AdPresenter
import com.chalkbox.propertyfinder.feed.adapters.PropertyAdapter
import com.chalkbox.propertyfinder.feed.adapters.SuggestionsAdapter
import com.chalkbox.propertyfinder.feed.adapters.TenantAdapter
import com.chalkbox.propertyfinder.feed.presenters.FeedPropertyPresenter
import com.chalkbox.propertyfinder.feed.presenters.FeedSuggestionsPresenter
import com.chalkbox.propertyfinder.feed.presenters.FeedTenantPresenter
import com.chalkbox.propertyfinder.navigation.Navigator
import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class FeedPresenterFacadeImpl @Inject constructor(application: Application, picasso: Picasso) :
    GroupPresenterFacade {

    private var feedNavigator: Navigator? = null
    override var navigator: Navigator?
        get() = feedNavigator
        set(value) {
            feedNavigator = value
            for (presenter in presenters) {
                presenter.navigator = feedNavigator
            }
        }

    private val presenters: MutableSet<DatabaseApiPresenter<*>>

    init {
        this.presenters = TreeSet(COMPARATOR)

        val suggestions = FeedSuggestionsPresenter(
            application = application,
            identifier = "Suggestion",
            sortOrder = -15,
            adapter = SuggestionsAdapter(application, picasso)
        )
        val properties = FeedPropertyPresenter(
            application = application,
            identifier = "Vibe",
            sortOrder = -10,
            adapter = PropertyAdapter(application, picasso)
        )

        val ads = AdPresenter(
            application = application,
            identifier = "Ad",
            sortOrder = 5,
            adapter = AdPresenterAdapter(application)
        )
        val tenants = FeedTenantPresenter(
            application = application,
            identifier = "City",
            sortOrder = 10,
            adapter = TenantAdapter(application, picasso)
        )

        this.presenters.addAll(
            listOf(
                suggestions,
                tenants,
                ads,
                properties
            )
        )
    }

    override fun bindView(container: ViewGroup) {
        val inflater = LayoutInflater.from(container.context)
        for (presenter in presenters) {
            val view = inflater.inflate(presenter.resId, null, false)
            presenter.bindView(view)
            container.addView(view)
        }
    }

    override fun onPause() {

    }

    override fun onResume() {

    }

    override fun unbindView() {
        for (presenter in presenters) {
            presenter.unbindView()
        }
    }

    companion object {
        private val COMPARATOR =
            Comparator<DatabaseApiPresenter<*>> { a, b -> a.sortOrder - b.sortOrder }
    }
}