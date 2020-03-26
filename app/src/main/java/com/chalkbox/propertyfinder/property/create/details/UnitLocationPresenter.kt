package com.chalkbox.propertyfinder.property.create.details

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojoFormatters.CountryFormatter
import com.chalkbox.propertyfinder.dto.pojoFormatters.RegionFormatter
import com.chalkbox.propertyfinder.dto.pojos.Country
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.property_create_unit_location.view.*


class UnitLocationPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val property: Property,
    private val country: Country?
) : CreatePresenter<View>(identifier, sortOrder) {

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.property_create_unit_location

    override val heading: String = context.resources.getString(R.string.unit_location_header)

    private val coder = Geocoder(context)

    private lateinit var countryEditText: TextInputEditText
    private lateinit var regionEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText

    private fun getLocationFromAddress(strAddress: String): GeoPoint? {
        try {
            coder.getFromLocationName(strAddress, 5)?.let { address ->
                return GeoPoint(address[0].latitude, address[0].longitude)
            }
        } catch (e: Exception) {
            // ERROR
            Log.d("UnitLocationPresenter", e.localizedMessage)
        }

        return null
    }

    override fun bindView(view: View) {
        super.bindView(view)

        countryEditText = view.countryValue
        regionEditText = view.regionValue
        addressEditText = view.addressValue

        country?.let {
            val formatter = CountryFormatter(it)
            countryEditText.isEnabled = false
            countryEditText.setText(formatter.displayName)
        }

        property.region?.let {
            val formatter = RegionFormatter(it)
            regionEditText.isEnabled = false
            regionEditText.setText(formatter.displayName)
        }
    }

    override fun updateInfo() {
        val address = addressEditText.text.toString()
        getLocationFromAddress(address)?.let {
            property.location = it
        }
        property.address = address
    }

    override fun updateError(): CreatePresenterResult =
        when {
            countryEditText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Country is required."
            )
            regionEditText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Region is required."
            )
            addressEditText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Address is required."
            )
            else -> CreatePresenterResult(true)
        }
}