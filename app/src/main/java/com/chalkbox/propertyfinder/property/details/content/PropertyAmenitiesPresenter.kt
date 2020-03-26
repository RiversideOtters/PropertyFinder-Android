package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.PropertyAmenity
import com.chalkbox.propertyfinder.views.RecyclerViewDivider
import kotlinx.android.synthetic.main.property_create_unit_other_details.view.*

class PropertyAmenitiesPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val amenities: List<PropertyAmenity>
) : PropertyContentPresenter<PropertyAmenity>(identifier, sortOrder) {

    override val showDivider: Boolean = true
    override val contentResId: Int = R.layout.property_create_unit_other_details

    override val heading: String = context.resources.getString(R.string.unit_amenities_header)

    private var adapter: PropertyAmenitiesAdapter<PropertyAmenity> =
        PropertyAmenitiesAdapter(
            context,
            amenities
        )

    override val shouldShow: Boolean
        get() = !amenities.isNullOrEmpty()

    override fun bindView(view: View) {
        super.bindView(view)

        view.recycler_view.adapter = adapter
        view.recycler_view.addItemDecoration(RecyclerViewDivider(view.context))
    }
}