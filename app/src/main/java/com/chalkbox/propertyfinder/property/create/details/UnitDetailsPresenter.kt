package com.chalkbox.propertyfinder.property.create.details

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.*
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import com.chalkbox.propertyfinder.views.CurrencyTextWatcher
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.property_create_unit_details.view.*
import java.util.*

class UnitDetailsPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val property: Property,
    private val country: Country?
) : CreatePresenter<View>(identifier, sortOrder) {

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.property_create_unit_details

    override val heading: String = context.resources.getString(R.string.unit_details_header)

    private lateinit var priceEditText: TextInputEditText
    private lateinit var detailsEditText: TextInputEditText
    private lateinit var titleEditText: TextInputEditText

    override fun bindView(view: View) {
        super.bindView(view)

        priceEditText = view.rentalPriceValue
        detailsEditText = view.propertyDescriptionValue
        titleEditText = view.propertyTitleValue

        priceEditText.addTextChangedListener(CurrencyTextWatcher())

        country?.let {
            view.propertyTypeLayout.visibility = View.VISIBLE
            view.roomTypeLayout.visibility = View.VISIBLE

            // Property Type
            view.propertyTypeLayout.setTitle(view.context.getText(R.string.property_type_label).toString())
            view.propertyTypeLayout.setValues(it.propertyType)
            view.propertyTypeLayout.onSettingValueChanged = { value: PropertySettingValue ->
                property.propertyType = value as PropertyType
            }

            // Rental Type
            view.rentalTypeLayout.setTitle(view.context.getText(R.string.rental_type_label).toString())
            view.rentalTypeLayout.setValues(
                listOf(
                    PropertySettingValue(mapOf("en_us" to "room")),
                    PropertySettingValue(mapOf("en_us" to "whole unit"))
                )
            )
            view.rentalTypeLayout.onSettingValueChanged = { value: PropertySettingValue ->
                property.rentalType = value
                when (value.name?.getOrDefault("en_us", "")) {
                    "room" -> {
                        view.roomTypeLayout.visibility = View.VISIBLE
                        view.roomTypeLayout.selectFirst()

                        view.roomCountLayout.visibility = View.GONE
                        property.roomCount = 0
                    }
                    "whole unit" -> {
                        view.roomTypeLayout.visibility = View.GONE
                        property.roomType = PropertyRoomType()

                        view.roomCountLayout.visibility = View.VISIBLE
                        view.roomCountLayout.selectFirst()
                    }
                }
            }

            // Room Type
            view.roomTypeLayout.setTitle(view.context.getText(R.string.room_type_label).toString())
            view.roomTypeLayout.setValues(country.roomType)
            view.roomTypeLayout.onSettingValueChanged = { value: PropertySettingValue ->
                property.roomType = value as PropertyRoomType
            }

            // Room Count
            view.roomCountLayout.setTitle(view.context.getText(R.string.room_count_label).toString())
            view.roomCountLayout.setValues(
                    listOf(
                        PropertySettingValue(mapOf("en_us" to "1")),
                        PropertySettingValue(mapOf("en_us" to "2")),
                        PropertySettingValue(mapOf("en_us" to "3")),
                        PropertySettingValue(mapOf("en_us" to "4"))
                    )
                    )
            view.roomCountLayout.onSettingValueChanged = { value: PropertySettingValue ->
                val rooms = value.name?.getOrDefault("en_us", "1")
                rooms?.toInt()?.let { roomCount ->
                    property.roomCount = roomCount
                }
            }

            // Pre-select
            view.rentalTypeLayout.selectFirst()
            view.propertyTypeLayout.selectFirst()
        } ?: run {
            view.propertyTypeLayout.visibility = View.GONE
            view.roomTypeLayout.visibility = View.GONE
        }
    }

    override fun updateInfo() {
        property.price = Price(
            Currency.getInstance(Locale.getDefault()).currencyCode,
            mapOf("month" to CurrencyTextWatcher.getValue(priceEditText))
        )
        property.details = detailsEditText.text.toString()
        property.name = titleEditText.text.toString()
    }

    override fun updateError(): CreatePresenterResult =
        when {
            priceEditText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Price is required."
            )
            detailsEditText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Details are required."
            )
            titleEditText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Title is required."
            )
            else -> CreatePresenterResult(true)
        }
}