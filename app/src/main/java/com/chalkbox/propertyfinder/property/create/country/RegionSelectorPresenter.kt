package com.chalkbox.propertyfinder.property.create.country

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.dto.pojos.Region
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import com.chalkbox.propertyfinder.views.RecyclerViewDivider
import kotlinx.android.synthetic.main.presenter_feed_layout.view.*

class RegionSelectorPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val property: Property? = null,
    private val regions: List<Region>,
    private val listener: Listener<Region>
) : CreatePresenter<View>(identifier, sortOrder), PresenterAdapter.Listener<Region> {

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.property_create_unit_other_details

    override val heading: String = context.resources.getString(R.string.unit_regions_header)

    private var adapter: RegionSelectorAdapter<Region> = RegionSelectorAdapter(context)

    private var selectedRegion: Region? = null

    override fun bindView(view: View) {
        super.bindView(view)

        adapter.list.clear()
        adapter.list.addAll(regions)

        adapter.listener = this

        view.recycler_view.adapter = adapter
        view.recycler_view.addItemDecoration(RecyclerViewDivider(view.context))
    }

    override fun updateInfo() {
        property?.region = selectedRegion
    }

    override fun updateError(): CreatePresenterResult = if (selectedRegion == null) {
        CreatePresenterResult(
            false,
            "Please select a region."
        )
    } else {
        CreatePresenterResult(true)
    }

    override fun onItemClick(item: Region?) {
        item?.let {
            selectedRegion = it
            listener.pickRegion(it)
        }
    }

    override fun onShowAllClick() {}

    interface Listener<T> {
        fun pickRegion(item: T)
    }
}