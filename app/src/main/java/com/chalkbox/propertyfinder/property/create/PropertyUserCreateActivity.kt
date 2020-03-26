package com.chalkbox.propertyfinder.property.create

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.api.firebase.database.UserApi
import com.chalkbox.propertyfinder.api.firebase.storage.StorageApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.dto.pojos.User
import com.chalkbox.propertyfinder.property.create.details.UserCreatePresenter
import com.chalkbox.propertyfinder.property.create.imagePicker.ImageHolder
import com.chalkbox.propertyfinder.property.create.imagePicker.ImagePicker
import com.chalkbox.propertyfinder.property.create.imagePicker.ImagePickerPresenter
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.activity_create.*
import javax.inject.Inject

class PropertyUserCreateActivity :
    PropertyCreateActivity(),
    ImagePicker.Listener,
    ImagePickerPresenter.Listener<Bitmap> {

    @Inject
    lateinit var storageApi: StorageApi

    @Inject
    lateinit var api: PropertyApi

    @Inject
    lateinit var userApi: UserApi

    @Inject
    lateinit var imageHolder: ImageHolder

    private val accountImageHolder: ImageHolder = ImageHolder()

    private lateinit var imagePicker: ImagePicker
    private lateinit var imagePickerPresenter: ImagePickerPresenter<Bitmap>
    private var user: User = User()

    override val propertyApi: PropertyApi?
        get() = api

    override val nextActivity: Class<*>? = null

    override fun setupPresenters() {
        AppComponentFactory()
            .newCreatePropertyComponent(application)
            .inject(this)

        imagePicker = ImagePicker(this, this)

        imagePickerPresenter =
            ImagePickerPresenter(
                context = this,
                identifier = "",
                title = this.resources.getString(R.string.account_details_header),
                sortOrder = -1,
                imageHolder = accountImageHolder,
                listener = this
            )
        val userDetails = UserCreatePresenter(
            context = this,
            identifier = "",
            sortOrder = 0,
            user = user
        )
        presenters.addAll(listOf(imagePickerPresenter, userDetails))
    }

    override fun updateInfo() {
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

    private fun createUser(propertyContent: Map<String, Any>) {
        userApi.create(user) { userId, propertyRef ->
            val mutableMap = mutableMapOf(
                ACCOUNT_ID_KEY to userId,
                ACCOUNT_DOCU_REF_KEY to propertyRef
            )
            for ((key, value) in propertyContent.iterator()) {
                mutableMap[key] = value
            }
            postUserCreate(mutableMap)
        }
    }

    private fun postUserCreate(content: Map<String, Any>) {
        val userId = content[ACCOUNT_ID_KEY]
        val userRef = content[ACCOUNT_DOCU_REF_KEY] as DocumentReference

        val propertyId = content[PROPERTY_ID_KEY]
        val propertyRef = content[PROPERTY_DOCU_REF_KEY] as DocumentReference

        (userId as? String)?.let { userId ->

            setInProgressStatus(true)
            progressBar.progress = 0

            storageApi.uploadImages(
                filenamePrefix = userId,
                bitmapList = accountImageHolder.list,
                onProgressListener = { progress ->
                    val intProgress = progress.toInt()
                    progressBar.progress = intProgress
                },
                onFailureListener = { error ->
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                    setInProgressStatus(false)
                },
                onCompleteListener = { filePaths ->
                    user.images.clear()
                    user.images.addAll(filePaths)
                    userRef.set(user)

                    property.user = userRef
                    propertyRef.set(property)

                    setInProgressStatus(false)
                    finishAffinity()
                })
        }
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

                    // Create user
                    createUser(content)
                })
        }
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
