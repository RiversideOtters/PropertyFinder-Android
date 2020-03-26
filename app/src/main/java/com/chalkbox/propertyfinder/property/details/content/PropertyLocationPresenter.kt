package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.FragmentManager
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint

class PropertyLocationPresenter(
    context: Context,
    private val fragmentManager: FragmentManager,
    identifier: String,
    sortOrder: Int,
    private val property: Property
) :
    PropertyContentPresenter<Property>(identifier, sortOrder), OnMapReadyCallback, Parcelable {

    override val heading: String =
        context.resources.getString(R.string.property_details_location_header)

    override val contentResId: Int = R.layout.property_location_layout

    override val shouldShow: Boolean
        get() = !(property.location == null || property.location?.compareTo(
            GeoPoint(
                0.0,
                0.0
            )
        ) == 0)

    constructor(parcel: Parcel) : this(
        TODO("context"),
        TODO("fragmentManager"),
        TODO("identifier"),
        TODO("sortOrder"),
        parcel.readParcelable(Property::class.java.classLoader)
    ) {
    }

    override fun bindView(view: View) {
        super.bindView(view)

        (fragmentManager.findFragmentById(R.id.propertyMapView) as? SupportMapFragment)?.getMapAsync(
            this
        )
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            val latLng =
                LatLng(property.location?.latitude ?: 0.0, property.location?.longitude ?: 0.0)
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
            addMarker(MarkerOptions().position(latLng))
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(property, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyLocationPresenter> {
        override fun createFromParcel(parcel: Parcel): PropertyLocationPresenter {
            return PropertyLocationPresenter(
                parcel
            )
        }

        override fun newArray(size: Int): Array<PropertyLocationPresenter?> {
            return arrayOfNulls(size)
        }
    }
}