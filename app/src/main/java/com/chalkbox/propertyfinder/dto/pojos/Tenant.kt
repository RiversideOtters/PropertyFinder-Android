package com.chalkbox.propertyfinder.dto.pojos

import android.os.Parcel
import android.os.Parcelable
import com.chalkbox.propertyfinder.dto.pojos.base.Pojo
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.util.*

open class Tenant(
    // Condo, landed, government housing
    var propertyType: PropertyType? = PropertyType(),

    // Master, common, shared
    var roomType: PropertyRoomType? = PropertyRoomType(),
    var price: Price? = Price(),
    var country: DocumentReference? = null,

    var name: String? = "",
    var age: Int = 0,
    var description: String? = "",
    var gender: PropertySettingValue? = PropertySettingValue(),
    var occupation: PropertySettingValue? = PropertySettingValue(),

    var phone: String? = "",
    var location: GeoPoint? = null,
    var address: String? = "",
    var region: PropertySettingValue? = PropertySettingValue(),
    var district: PropertySettingValue? = PropertySettingValue(),

    var images: MutableList<String> = mutableListOf(),

    var user: DocumentReference? = null

) : Pojo, Parcelable {
    companion object CREATOR : Parcelable.Creator<Tenant> {
        override fun createFromParcel(parcel: Parcel) = Tenant(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Tenant>(size)
    }

    private constructor(parcel: Parcel) : this() {
        propertyType = parcel.readParcelable(PropertyType::class.java.classLoader)
        roomType = parcel.readParcelable(PropertyRoomType::class.java.classLoader)
        price = parcel.readParcelable(Price::class.java.classLoader)
        // Country

        name = parcel.readString()
        age = parcel.readInt()
        description = parcel.readString()
        gender = parcel.readParcelable(PropertySettingValue::class.java.classLoader)
        propertyType = parcel.readParcelable(PropertySettingValue::class.java.classLoader)

        phone = parcel.readString()
        val lat = parcel.readDouble()
        val long = parcel.readDouble()
        location = GeoPoint(lat, long)
        address = parcel.readString()
        region = parcel.readParcelable(PropertySettingValue::class.java.classLoader)
        district = parcel.readParcelable(PropertySettingValue::class.java.classLoader)

        parcel.readList(images, String::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(propertyType, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(roomType, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(price, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        // Country

        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(description)
        parcel.writeParcelable(gender, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(occupation, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)

        parcel.writeString(phone)
        parcel.writeDouble(location?.latitude ?: 0.0)
        parcel.writeDouble(location?.longitude ?: 0.0)
        parcel.writeString(address)
        parcel.writeParcelable(region, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(district, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)

        // User
    }

    override fun describeContents() = 0
}

