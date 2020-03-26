package com.chalkbox.propertyfinder.property.create.details

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.dto.pojos.PropertyAmenity
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import com.chalkbox.propertyfinder.views.RecyclerViewDivider
import kotlinx.android.synthetic.main.property_create_unit_other_details.view.*

class UnitAmenitiesPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val property: Property? = null,
    amenities: List<PropertyAmenity>
) : CreatePresenter<View>(identifier, sortOrder) {

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.property_create_unit_other_details

    override val heading: String = context.resources.getString(R.string.unit_amenities_header)

    private var adapter: UnitAmenitiesAdapter<PropertyAmenity> =
        UnitAmenitiesAdapter(context, amenities)

    override fun bindView(view: View) {
        super.bindView(view)

        view.recycler_view.adapter = adapter
        view.recycler_view.addItemDecoration(RecyclerViewDivider(view.context))
    }

    override fun updateInfo() {
        property?.amenities?.addAll(adapter.selectedAmenities())
    }

    override fun updateError(): CreatePresenterResult =
        CreatePresenterResult(true)
}