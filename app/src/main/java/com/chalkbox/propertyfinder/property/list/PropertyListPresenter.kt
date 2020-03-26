package com.chalkbox.propertyfinder.property.list

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.chalkbox.propertyfinder.api.firebase.filters.PropertyFilter
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.base.presenters.Presenter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.intents.IntentFactory.Companion.PROPERTY_DETAILS_EXTRA
import com.chalkbox.propertyfinder.navigation.Navigator
import com.chalkbox.propertyfinder.property.details.PropertyDetailsActivity
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_property_list.view.*
import javax.inject.Inject

class PropertyListPresenter(
    application: Application,
    lifecycleOwner: LifecycleOwner,
    private val navigator: Navigator?,
    picasso: Picasso
) : Presenter<View>(),
    ListAdapter.Listener<Property> {
    @Inject
    internal lateinit var propertyFilter: PropertyFilter

    override val resId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    private val mAdapter: FirestorePagingAdapter<Property, ListViewHolder<Property>>

    override fun bindView(view: View) {
        super.bindView(view)

        view.swipeRefreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }

        view.recyclerView.adapter = mAdapter
    }

    init {
        AppComponentFactory()
            .newPropertyListActivityComponent(application)
            .inject(this)

        mAdapter = ListAdapter(
            application,
            this,
            PagingOptionsFactory(lifecycleOwner, propertyFilter).options,
            picasso
        )
    }

    override fun onLoadingStart() {
        view?.swipeRefreshLayout?.isRefreshing = true
    }

    override fun onLoadingFinished() {
        view?.swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(item: Property) {
        navigator?.navigateTo(PropertyDetailsActivity::class.java, mapOf(PROPERTY_DETAILS_EXTRA to item))
    }
}