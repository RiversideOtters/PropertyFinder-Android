package com.chalkbox.propertyfinder.feed.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertyFormatter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_content_property.view.*

class PropertyAdapter<T : Property>(private val context: Context, private val picasso: Picasso) :
    PresenterAdapter<T>(context) {
    override val layout: Int = R.layout.card_view_feed_property

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<T> {
        val view = layoutInflater.inflate(layout, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener(viewHolder)
        return viewHolder
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<T>(view) {
        override fun setItem(item: T) {
            item.images.firstOrNull()?.let { img ->
                if (img.isNotEmpty())
                    picasso.load(img).into(view.cardViewImage)
            }

            val propertyFormatter = PropertyFormatter(item)
            view.cardViewTitle.text = propertyFormatter.displayName
            view.cardViewPrice.text = if (propertyFormatter.displayMonthlyPrice.isNotEmpty()) {
                propertyFormatter.displayMonthlyPrice
            } else {
                context.getText(R.string.inquire_price)
            }
            view.propertyAvailability.text = propertyFormatter.displayAvailability
        }

        override fun onClick(v: View) {
            listener?.onItemClick(list[adapterPosition])
        }
    }
}