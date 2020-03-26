package com.chalkbox.propertyfinder.property.create.details

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.dto.pojos.PropertySetting
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import kotlinx.android.synthetic.main.property_create_unit_other_details.view.*


class UnitOtherDetailsPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val settings: List<PropertySetting>,
    private val property: Property
) : CreatePresenter<View>(identifier, sortOrder) {

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.property_create_unit_other_details

    override val heading: String = context.resources.getString(R.string.unit_other_details_header)
    private lateinit var adapter: UnitOtherDetailsAdapter<PropertySetting>

    override fun bindView(view: View) {
        super.bindView(view)

        if (settings.isNotEmpty()) {
            view.visibility = View.VISIBLE

            adapter = UnitOtherDetailsAdapter(view.context, settings)
            view.recycler_view.adapter = adapter

        } else {
            view.visibility = View.GONE
        }
    }

    override fun updateInfo() {
        val list = mutableListOf<PropertySetting>()
        list.addAll(adapter.selectedPropertySettings())
        property.settings = list
    }

    override fun updateError(): CreatePresenterResult =
        CreatePresenterResult(true)
}