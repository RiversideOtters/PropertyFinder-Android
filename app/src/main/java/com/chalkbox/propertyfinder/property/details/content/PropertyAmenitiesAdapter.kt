package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertyAmenityFormatter
import com.chalkbox.propertyfinder.dto.pojos.PropertyAmenity
import kotlinx.android.synthetic.main.property_create_amenity_view_holder.view.*

class PropertyAmenitiesAdapter<T : PropertyAmenity>(context: Context, amenities: List<PropertyAmenity>) :
    PresenterAdapter<PropertyAmenity>(context) {
    override val layout: Int = R.layout.property_create_amenity_view_holder

    init {
        list.addAll(amenities)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<PropertyAmenity> {
        val view = layoutInflater.inflate(layout, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener(viewHolder)
        return viewHolder
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<PropertyAmenity>(view) {
        override fun setItem(item: PropertyAmenity) {
            val formatter = PropertyAmenityFormatter(item)
            view.amenityCheckbox.text = formatter.displayName
            view.amenityCheckbox.isChecked = true
            view.amenityCheckbox.isEnabled = false
        }

        override fun onClick(v: View) {
            listener?.onItemClick(list[adapterPosition])
        }
    }
}