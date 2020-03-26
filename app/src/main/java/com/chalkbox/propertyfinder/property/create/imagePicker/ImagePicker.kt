package com.chalkbox.propertyfinder.property.create.imagePicker

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.io.IOException
import java.util.*

class ImagePicker(private val activity: Activity, private val listener: Listener) {

    interface Listener {
        fun onImagedPicked(bitmap: Bitmap)
    }

    private var permissionsToRequest: ArrayList<String>

    private val permissions = ArrayList<String>()
    private val permissionsRejected = ArrayList<String>()
    private var picUri: Uri? = null

    companion object {
        const val ALL_PERMISSIONS_RESULT = 107
    }

    init {
        permissions.add(Manifest.permission.CAMERA)
        permissionsToRequest = findUnAskedPermissions(permissions)

        if (permissionsToRequest.isNotEmpty()) {
            activity.requestPermissions(
                permissionsToRequest.toTypedArray(),
                ALL_PERMISSIONS_RESULT
            )
        } else {
//            showImagePicker()
        }
    }

    fun showImagePicker() {
        activity.startActivityForResult(getPickImageChooserIntent(), 200)
    }

    /**
     * Create radio_button_pressed chooser intent to select the source to get image from.<br></br>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br></br>
     * All possible sources are added to the intent chooser.
     */
    private fun getPickImageChooserIntent(): Intent {

        // Determine Uri of camera image to save.
        val outputFileUri = getCaptureImageOutputUri()

        val allIntents = ArrayList<Intent>()
        val packageManager = activity.packageManager

        // collect all camera intents
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            }
            allIntents.add(intent)
        }

        // collect all gallery intents
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            allIntents.add(intent)
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        var mainIntent = allIntents[allIntents.size - 1]
        for (intent in allIntents) {
            if (intent.component?.className == "com.android.documentsui.DocumentsActivity") {
                mainIntent = intent
                break
            }
        }
        allIntents.remove(mainIntent)

        // Create radio_button_pressed chooser from the main intent
        val chooserIntent = Intent.createChooser(mainIntent, "Select source")

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())

        return chooserIntent
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage = activity.externalCacheDir
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "profile.png"))
        }
        return outputFileUri
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val bitmap: Bitmap
        if (resultCode == Activity.RESULT_OK) {
            var myBitmap: Bitmap? = null

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data)

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, picUri)
                    myBitmap = rotateImageIfRequired(myBitmap, picUri!!)
                    myBitmap = getResizedBitmap(myBitmap, 500)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                myBitmap = data?.extras?.run { get("data") } as Bitmap
            }

            myBitmap?.let {
                listener.onImagedPicked(it)
            }
        }
    }

    @Throws(IOException::class)
    private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap {

        val ei = ExifInterface(selectedImage.path)
        val orientation =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> return rotateImage(img, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> return rotateImage(img, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> return rotateImage(img, 270)
            else -> return img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 0) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }


    /**
     * Get the URI of the selected image from [.getPickImageChooserIntent].<br></br>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    private fun getPickImageResultUri(data: Intent?): Uri? {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }

        return if (isCamera) getCaptureImageOutputUri() else data?.data
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("pic_uri", picUri)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        picUri = savedInstanceState.getParcelable("pic_uri")
    }

    private fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String> {
        val result = ArrayList<String>()

        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }

        return result
    }

    private fun hasPermission(permission: String): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return activity.checkSelfPermission(permission) === android.content.pm.PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(requestCode: Int) {
        when (requestCode) {
            ALL_PERMISSIONS_RESULT -> {
                for (perms in permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {
                        permissionsRejected.add(perms)
                    }
                }

                if (permissionsRejected.size > 0) {
                    if (activity.shouldShowRequestPermissionRationale(permissionsRejected[0])) {
                        showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                            DialogInterface.OnClickListener { _, _ ->
                                activity.requestPermissions(
                                    permissionsRejected.toTypedArray(),
                                    ALL_PERMISSIONS_RESULT
                                )
                            })
                        return
                    }
                }
            }
        }
    }
}