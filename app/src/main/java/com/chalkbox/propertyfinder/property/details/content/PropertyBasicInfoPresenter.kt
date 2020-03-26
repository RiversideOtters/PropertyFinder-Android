package com.chalkbox.propertyfinder.property.details.content

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertyFormatter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.dto.pojos.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.property_image_view_holder.view.*
import kotlinx.android.synthetic.main.property_main_info_layout.view.*

class PropertyBasicInfoPresenter(
    identifier: String,
    sortOrder: Int,
    private val property: Property,
    private val picasso: Picasso) :
    PropertyContentPresenter<Property>(identifier, sortOrder) {

    override val heading: String = ""

    override val contentResId: Int = R.layout.property_main_info_layout

    override val shouldShow: Boolean = true

    override val showDivider: Boolean = false

    lateinit var propertyOwnerName: TextView
    lateinit var propertyOwnerImage: ImageView

    override fun bindView(view: View) {
        super.bindView(view)

        propertyOwnerName = view.propertyOwnerName
        propertyOwnerImage = view.propertyOwnerImage

        val propertyFormatter = PropertyFormatter(property)

        view.propertyOwnerImage.visibility = View.GONE

        if (propertyFormatter.propertyTypes.isNotEmpty()) {
            view.propertyType.text = propertyFormatter.propertyTypes
            view.propertyType.visibility = View.VISIBLE
        } else {
            view.propertyType.visibility = View.GONE
        }

        view.propertyName.text = propertyFormatter.displayName
        view.propertyAddress.text = propertyFormatter.displayAddress
        view.propertyAvailability.text = propertyFormatter.displayAvailability
    }

    fun setUser(user: User) {
        propertyOwnerName.text = "Posted by ${user.name}"
        user.images.firstOrNull()?.let {
            picasso.load(it).into(propertyOwnerImage)
            propertyOwnerImage.visibility = View.VISIBLE
        }
    }
}