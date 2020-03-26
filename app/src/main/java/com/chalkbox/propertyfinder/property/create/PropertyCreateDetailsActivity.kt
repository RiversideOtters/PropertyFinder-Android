package com.chalkbox.propertyfinder.property.create

import android.os.Bundle
import android.widget.Toast
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.api.firebase.storage.StorageApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.property.create.details.UnitAmenitiesPresenter
import com.chalkbox.propertyfinder.property.create.details.UnitDetailsPresenter
import com.chalkbox.propertyfinder.property.create.details.UnitLocationPresenter
import com.chalkbox.propertyfinder.property.create.details.UnitOtherDetailsPresenter
import com.chalkbox.propertyfinder.property.create.imagePicker.ImageHolder
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.activity_create.*
import javax.inject.Inject

class PropertyCreateDetailsActivity : PropertyCreateActivity() {
    override val nextActivity: Class<*>? = PropertyUserCreateActivity::class.java

    @Inject
    lateinit var storageApi: StorageApi

    @Inject
    lateinit var api: PropertyApi

    @Inject
    lateinit var imageHolder: ImageHolder

    override val propertyApi: PropertyApi?
        get() = api

    override fun setupPresenters() {
        val unitDetails = UnitDetailsPresenter(
            context = this,
            identifier = "",
            sortOrder = 0,
            property = property,
            country = country
        )
        val unitLocation = UnitLocationPresenter(
            context = this,
            identifier = "",
            sortOrder = 0,
            property = property,
            country = country
        )
        val unitOtherDetails = UnitOtherDetailsPresenter(
            context = this,
            identifier = "",
            sortOrder = 0,
            property = property,
            settings = country?.settings ?: listOf()
        )
        val unitAmenities = UnitAmenitiesPresenter(
            context = this,
            identifier = "",
            sortOrder = 0,
            property = property,
            amenities = country?.amenities ?: listOf()
        )

        presenters.addAll(
            listOf(
                unitDetails,
                unitLocation,
                unitOtherDetails,
                unitAmenities
            )
        )
    }

    override fun updateInfo() {
        presenters.forEach { (it as? CreatePresenter<*>)?.updateInfo() }
    }

    override fun postCreate(content: Map<String, Any>) {
        val id = content[PROPERTY_ID_KEY]
        val ref = content[PROPERTY_DOCU_REF_KEY] as DocumentReference

        (id as? String)?.let { propertyId ->

            setInProgressStatus(true)

            storageApi.uploadImages(
                filenamePrefix = propertyId,
                bitmapList = imageHolder.list,
                onProgressListener = { progress ->
                    val intProgress = progress.toInt()
                    progressBar.progress = intProgress
                },
                onFailureListener = { error ->
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                    setInProgressStatus(false)
                },
                onCompleteListener = { filePaths ->
                    property.images.clear()
                    property.images.addAll(filePaths)
                    ref.set(property)
                    setInProgressStatus(false)
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppComponentFactory()
            .newCreatePropertyComponent(application)
            .inject(this)
    }
}