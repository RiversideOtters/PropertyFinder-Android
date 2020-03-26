package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertyFormatter
import com.chalkbox.propertyfinder.dto.pojos.Property
import kotlinx.android.synthetic.main.property_extra_info_layout.view.*

class PropertyExtraInfoPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val property: Property
) :
    PropertyContentPresenter<Property>(identifier, sortOrder) {

    override val heading: String =
        context.resources.getString(R.string.property_details_info_header)

    override val contentResId: Int = R.layout.property_extra_info_layout

    override val shouldShow: Boolean
        get() = !property.details.isNullOrEmpty()

    override fun bindView(view: View) {
        super.bindView(view)

        val propertyFormatter = PropertyFormatter(property)
        view.propertyDetails.text = propertyFormatter.displayDetails
    }
}