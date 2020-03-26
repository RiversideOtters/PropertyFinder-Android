package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.google.firebase.firestore.GeoPoint

class PropertyNearbyToDoPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val property: Property
) :
    PropertyContentPresenter<Property>(identifier, sortOrder) {

    override val heading: String =
        context.resources.getString(R.string.property_details_nearby_header)

    override val contentResId: Int = R.layout.property_nearby_to_do_layout

    override val shouldShow: Boolean
        get() = !(property.location == null || property.location?.compareTo(
            GeoPoint(
                0.0,
                0.0
            )
        ) == 0)

    override fun bindView(view: View) {
        super.bindView(view)

    }
}