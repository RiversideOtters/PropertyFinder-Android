package com.chalkbox.propertyfinder.property.list

import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertyFormatter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_content_property.view.*

open class ListViewHolder<T: Property>(private val view: View, private val picasso: Picasso): PresenterViewHolder<T>(view) {
    val layout: Int = R.layout.card_view_property

    override fun setItem(item: T) {
        item.images.firstOrNull()?.let { img ->
            picasso.load(img).into(view.cardViewImage)
        }
        val propertyFormatter = PropertyFormatter(item)
        view.cardViewTitle.text = propertyFormatter.displayName
        view.cardViewPrice.text = propertyFormatter.displayMonthlyPrice
        view.propertyAvailability.text = propertyFormatter.displayAvailability
    }
}

