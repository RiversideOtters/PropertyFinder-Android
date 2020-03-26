package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.PropertySetting
import kotlinx.android.synthetic.main.property_create_unit_other_details.view.*

class PropertyOtherDetailsPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val settings: List<PropertySetting>
) : PropertyContentPresenter<PropertySetting>(identifier, sortOrder) {

    override val showDivider: Boolean = true
    override val contentResId: Int = R.layout.property_create_unit_other_details

    override val heading: String = context.resources.getString(R.string.unit_other_details_header)

    private lateinit var adapter: PropertyOtherDetailsAdapter<PropertySetting>

    override val shouldShow: Boolean
        get() = !settings.isNullOrEmpty()

    override fun bindView(view: View) {
        super.bindView(view)

        adapter = PropertyOtherDetailsAdapter(
            view.context,
            settings
        )
        view.recycler_view.adapter = adapter
    }
}