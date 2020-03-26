package com.chalkbox.propertyfinder.property.create.details

import android.content.Context
import android.view.View
import android.widget.RadioButton
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertyFormatter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.dto.pojos.PropertySettingValue
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import kotlinx.android.synthetic.main.property_create_unit_availability.view.*
import java.util.*


class UnitAvailabilityPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val property: Property,
    private val listener: Listener
) : CreatePresenter<View>(identifier, sortOrder) {

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.property_create_unit_availability

    override val heading: String = context.resources.getString(R.string.unit_availability_header)

    private lateinit var availabilityRadioButton: RadioButton

    private lateinit var selectedDate: Date

    private var selectedLeaseType: PropertySettingValue? = null

    override fun bindView(view: View) {
        super.bindView(view)

        // Availability
        availabilityRadioButton = view.availabilityValue
        availabilityRadioButton.setOnClickListener {
            availabilityRadioButton.isSelected = false
            listener.showDatePicker()
        }
        setDate(Date())

        // Stay Duration
        view.leaseTypeLayout.setTitle(view.context.getText(R.string.stay_duration_label).toString())
        view.leaseTypeLayout.setValues(
            listOf(
                PropertySettingValue(mapOf("en_us" to "Flexible")),
                PropertySettingValue(mapOf("en_us" to "Short Term")),
                PropertySettingValue(mapOf("en_us" to "> 1 Year"))
            )
        )
        view.leaseTypeLayout.onSettingValueChanged = { value: PropertySettingValue ->
            selectedLeaseType = value
        }
    }

    fun setDate(date: Date) {
        val formatter = PropertyFormatter(property)
        selectedDate = date
        availabilityRadioButton.text = formatter.displayFormat(selectedDate)
    }

    override fun updateInfo() {
        property.availability = selectedDate
        property.stayDuration = selectedLeaseType
    }

    override fun updateError(): CreatePresenterResult =
        when (selectedLeaseType) {
            null -> CreatePresenterResult(
                false,
                "Please pick a lease type."
            )
            else -> CreatePresenterResult(true)
        }

    interface Listener {
        fun showDatePicker()
    }
}