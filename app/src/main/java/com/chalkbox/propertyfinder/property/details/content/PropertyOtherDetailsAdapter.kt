package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertySettingFormatter
import com.chalkbox.propertyfinder.dto.pojos.PropertySetting
import kotlinx.android.synthetic.main.property_create_unit_other_details_view_holder.view.*

class PropertyOtherDetailsAdapter<T : PropertySetting>(
    context: Context,
    values: List<PropertySetting>
) :
    PresenterAdapter<PropertySetting>(context) {
    override val layout: Int = R.layout.property_create_unit_other_details_view_holder

    init {
        list.addAll(values)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PresenterViewHolder<PropertySetting> {
        val view = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<PropertySetting>(view) {
        override fun setItem(item: PropertySetting) {
            view.radioGroup.setTitle(PropertySettingFormatter(item).displayName)
            view.radioGroup.setValues(item.values, item.values.firstOrNull())
        }

        override fun onClick(v: View) {

        }
    }
}