package com.chalkbox.propertyfinder.property.create.country

import android.app.Application
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.api.firebase.database.CountryApi
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.dto.pojos.Country
import com.chalkbox.propertyfinder.feed.presenters.base.DatabaseApiPresenter
import com.chalkbox.propertyfinder.views.RecyclerViewDivider
import kotlinx.android.synthetic.main.presenter_feed_layout.view.*
import javax.inject.Inject


class CountrySelectorPresenter(
    application: Application,
    identifier: String,
    sortOrder: Int,
    private val listener: Listener<Country>?,
    adapter: PresenterAdapter<Country>
) : DatabaseApiPresenter<Country>(identifier, sortOrder, adapter),
    PresenterAdapter.Listener<Country> {

    @Inject
    internal lateinit var presenterApi: CountryApi

    override val api: DatabaseApi<Country>
        get() = presenterApi

    override val heading: String = application.resources.getString(R.string.country_picker_header)

    override val resId: Int = R.layout.presenter_feed_vertical_layout

    init {
        AppComponentFactory()
            .newCreatePropertyComponent(application)
            .inject(this)
    }

    override fun bindView(view: View) {
        super.bindView(view)

        view.recycler_view.adapter = adapter
        adapter.listener = this

        view.recycler_view.addItemDecoration(RecyclerViewDivider(view.context))
    }

    override fun onItemClick(item: Country?) {
        item?.let {
            listener?.pickCountry(it)
        }
    }

    override fun onShowAllClick() {}

    interface Listener<T> {
        fun pickCountry(item: T)
    }
}