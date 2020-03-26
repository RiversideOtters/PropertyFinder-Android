package com.chalkbox.propertyfinder.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertySettingValueFormatter
import com.chalkbox.propertyfinder.dto.pojos.PropertySettingValue
import kotlinx.android.synthetic.main.property_create_unit_other_details_values.view.*

class SettingValuesRadioGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var radioGroup: RadioGroup
    private val values = mutableListOf<PropertySettingValue>()

    var onSettingValueChanged: ((value: PropertySettingValue) -> Unit) = { _ -> }

    init {
        inflate(context, R.layout.property_create_unit_other_details_values, this)

        visibility = View.GONE

        radioGroup = this.otherDetailsRadioGroup
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (values.size > checkedId) {
                onSettingValueChanged(values[checkedId])
            }
        }
    }

    fun selectFirst() {
        radioGroup.check(0)
    }

    fun setTitle(title: String) {
        otherDetailsTitle.text = title
    }

    fun setValues(list: List<PropertySettingValue>, selectedValue: PropertySettingValue? = null) {
        values.clear()
        values.addAll(list)

        visibility = if (values.isEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }

        val params = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.WRAP_CONTENT,
            RadioGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 8, 0)

        for ((i, type) in values.withIndex()) {
            val rb: RadioButton = inflate(this.context, R.layout.radio_button, null) as RadioButton
            rb.layoutParams = params
            rb.id = i

            val formatter = PropertySettingValueFormatter(type)
            rb.text = formatter.displayName

            otherDetailsRadioGroup.addView(rb)
        }

        selectedValue?.let {
            val index = values.indexOf(it)
            otherDetailsRadioGroup.check(index)
        }
    }
}
