package com.chalkbox.propertyfinder.dto.pojos

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
import com.chalkbox.propertyfinder.dto.pojos.base.Pojo
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.util.*

open class Property(
    var address: String? = "",
    var availability: Date? = Date(),
    var stayDuration: PropertySettingValue? = PropertySettingValue(),
    var rentalType: PropertySettingValue? = PropertySettingValue(),
    var details: String? = "",
    var images: MutableList<String> = mutableListOf(),
    var location: GeoPoint? = null,
    var name: String? = "",
    var price: Price? = Price(),
    var propertyType: PropertyType? = PropertyType(),
    var roomType: PropertyRoomType? = PropertyRoomType(),
    var roomCount: Int = 0,
    var viewCount: Int = 0,
    var user: DocumentReference? = null,

    // Creating
    var country: Country? = Country(),
    var region: Region? = Region(),
    var amenities: MutableList<PropertyAmenity> = mutableListOf(),
    var settings: MutableList<PropertySetting> = mutableListOf()
) : Pojo, Parcelable {
    companion object CREATOR : Parcelable.Creator<Property> {
        override fun createFromParcel(parcel: Parcel) = Property(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Property>(size)
    }

    private constructor(parcel: Parcel) : this() {
        address = parcel.readString()
        parcel.readList(amenities, PropertyAmenity::class.java.classLoader)
        availability = Date(parcel.readLong())
        country = parcel.readParcelable(Country::class.java.classLoader)
        region = parcel.readParcelable(Region::class.java.classLoader)
        stayDuration = parcel.readParcelable(PropertySettingValue::class.java.classLoader)
        rentalType = parcel.readParcelable(PropertySettingValue::class.java.classLoader)
        details = parcel.readString()
        parcel.readList(images, String::class.java.classLoader)
        val lat = parcel.readDouble()
        val long = parcel.readDouble()
        location = GeoPoint(lat, long)
        name = parcel.readString()
        price = parcel.readParcelable(Price::class.java.classLoader)
        propertyType = parcel.readParcelable(PropertyType::class.java.classLoader)
        roomType = parcel.readParcelable(PropertyRoomType::class.java.classLoader)
        parcel.readList(settings, PropertySetting::class.java.classLoader)
        roomCount = parcel.readInt()
        viewCount = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeList(amenities)
        parcel.writeLong(availability?.time ?: -1)
        parcel.writeParcelable(country, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(region, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(stayDuration, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(rentalType, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeString(details)
        parcel.writeList(images)
        parcel.writeDouble(location?.latitude ?: 0.0)
        parcel.writeDouble(location?.longitude ?: 0.0)
        parcel.writeString(name)
        parcel.writeParcelable(price, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(propertyType, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(roomType, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeList(settings)
        parcel.writeInt(roomCount)
        parcel.writeInt(viewCount)
    }

    override fun describeContents() = 0
}
