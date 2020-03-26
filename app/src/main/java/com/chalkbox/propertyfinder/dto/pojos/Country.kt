package com.chalkbox.propertyfinder.dto.pojos

import android.os.Parcelable
import com.chalkbox.propertyfinder.dto.pojos.base.LocaleId
import com.chalkbox.propertyfinder.dto.pojos.base.Pojo
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Country(
    var key: String = "",
    var name: Map<String, String>? = mapOf(),
    var regions: MutableList<Region> = mutableListOf(),
    var propertyType: MutableList<PropertyType> = mutableListOf(),
    var roomType: MutableList<PropertyRoomType> = mutableListOf(),
    var amenities: MutableList<PropertyAmenity> = mutableListOf(),
    var settings: MutableList<PropertySetting> = mutableListOf()
) : Pojo, Parcelable

@Parcelize
open class PropertySetting(
    var name: Map<LocaleId, String>? = mapOf(),
    var values: MutableList<PropertySettingValue> = mutableListOf()
) : Pojo, Parcelable

@Parcelize
open class PropertySettingValue(
    open var name: Map<LocaleId, String>? = mapOf()
) : Pojo, Parcelable

@Parcelize
open class PropertyRoomType(override var name: Map<LocaleId, String>? = mapOf()) :
    PropertySettingValue(name)

@Parcelize
open class PropertyType(override var name: Map<LocaleId, String>? = mapOf()) :
    PropertySettingValue(name)

@Parcelize
open class PropertyAmenity(override var name: Map<LocaleId, String>? = mapOf()) :
    PropertySettingValue(name)

@Parcelize
open class Region(override var name: Map<LocaleId, String>? = mapOf()) :
    PropertySettingValue(name)