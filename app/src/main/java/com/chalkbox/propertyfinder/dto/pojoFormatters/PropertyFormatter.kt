package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.Price
import com.chalkbox.propertyfinder.dto.pojos.Property
import java.text.NumberFormat
import java.util.*

class PropertyFormatter<T : Property>(private val item: T) : PojoFormatter() {
    val displayAddress: String = displayFormat(item.address)
    val displayAvailability: String = displayFormat(item.availability)
    val displayDetails: String = displayFormat(item.details)
    val displayName: String = displayFormat(item.name)
    val displayDailyPrice: String
        get() = item.price?.let { displayPrice(it, it.daily) } ?: run { "" }
    val displayWeeklyPrice: String
        get() = item.price?.let { displayPrice(it, it.weekly) } ?: run { "" }
    val displayMonthlyPrice: String
        get() = item.price?.let { displayPrice(it, it.monthly) } ?: run { "" }
    val propertyTypes: String
        get() {
            val types = mutableListOf<String>()
            item.propertyType?.let {
                types.add(PropertyTypeFormatter(it).displayName)
            }
            item.rentalType?.let {
                types.add(PropertySettingValueFormatter(it).displayName)
            }
            item.roomType?.let {
                types.add(PropertyRoomTypeFormatter(it).displayName)
            }
            return types.filter { it.isNotEmpty() }.joinToString(separator = " • ") { it.toUpperCase(Locale.getDefault()) }
        }

    private fun displayPrice(price: Price, level: Int): String =
        if (level > 0) {
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance(price.currency)
            format.format(price.monthly)
        } else {
            ""
        }
}