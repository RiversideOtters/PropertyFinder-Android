package com.chalkbox.propertyfinder.property.create

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.dto.pojos.Country
import com.chalkbox.propertyfinder.property.create.country.CountrySelectorAdapter
import com.chalkbox.propertyfinder.property.create.country.CountrySelectorPresenter
import com.chalkbox.propertyfinder.property.create.imagePicker.ImageHolder
import com.chalkbox.propertyfinder.property.create.imagePicker.ImagePicker
import com.chalkbox.propertyfinder.property.create.imagePicker.ImagePickerPresenter
import javax.inject.Inject

class PropertyCreateImageAndCountryActivity :
    PropertyCreateActivity(),
    ImagePicker.Listener,
    ImagePickerPresenter.Listener<Bitmap>,
    CountrySelectorPresenter.Listener<Country> {

    @Inject
    lateinit var imageHolder: ImageHolder

    private lateinit var imagePicker: ImagePicker
    private lateinit var imagePickerPresenter: ImagePickerPresenter<Bitmap>

    override val nextButtonTitle: String = ""

    override val propertyApi: PropertyApi? = null

    override val nextActivity: Class<*> = PropertyCreateRegionActivity::class.java

    override fun setupPresenters() {
        AppComponentFactory()
            .newCreatePropertyComponent(application)
            .inject(this)

        imagePicker = ImagePicker(this, this)

        imagePickerPresenter =
            ImagePickerPresenter(
                context = this,
                identifier = "",
                sortOrder = -1,
                imageHolder = imageHolder,
                listener = this
            )
        val countrySelectorPresenter =
            CountrySelectorPresenter(
                application = application,
                identifier = "",
                sortOrder = 0,
                listener = this,
                adapter = CountrySelectorAdapter(this)
            )

        presenters.addAll(listOf(imagePickerPresenter, countrySelectorPresenter))
    }

    override fun updateInfo() {
        country?.let {
            property.country = Country(name = it.name, key = it.key)
        }
        imagePickerPresenter.updateInfo()
    }

    /*
     * Image Pickers
     */
    override fun onImagedPicked(bitmap: Bitmap) {
        imagePickerPresenter.addBitmap(bitmap)
    }

    override fun addImage() {
        imagePicker.showImagePicker()
    }

    override fun pickCountry(item: Country) {
        country = item

        val error = imagePickerPresenter.updateError()
        if (!error.result) {
            Toast.makeText(this, error.description, Toast.LENGTH_LONG).show()
            return
        }

        next()
    }

    /*
     * Below overrides are required by Image Picker
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        imagePicker.onSaveInstanceState(outState)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imagePicker.onRestoreInstanceState(savedInstanceState)
    }


    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imagePicker.onRequestPermissionsResult(requestCode)
    }
}
