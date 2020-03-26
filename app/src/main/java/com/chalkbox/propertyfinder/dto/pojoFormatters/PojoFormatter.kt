package com.chalkbox.propertyfinder.dto.pojoFormatters

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

abstract class PojoFormatter {
    fun displayFormat(localeMap: Map<String, String>?): String =
        localeMap?.get(Locale.getDefault().toString())?.let {
            it
        } ?: localeMap?.getOrDefault("en_us", "")?.let {
            it
        } ?: run {
            ""
        }

    fun displayFormat(string: String?): String = string?.let { it } ?: run { "" }

    fun displayFormat(date: Date?): String = date?.let {

        val availableDate = it.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        val today = LocalDateTime.now()
        val daysAway = ChronoUnit.DAYS.between(today, availableDate)
        return when {
            daysAway <= 0L -> "Available now"
            daysAway == 1L -> "Available tomorrow"
            daysAway < 11L -> "Available in $daysAway days"
            daysAway <= 30L -> {
                val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(it)
                "Available on $format • $daysAway days away"
            }
            else -> {
                val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(it)
                "Available on $format"
            }
        }

    } ?: run { "" }
}