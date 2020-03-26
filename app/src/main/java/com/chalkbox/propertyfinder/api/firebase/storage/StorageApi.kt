package com.chalkbox.propertyfinder.api.firebase.storage

import android.graphics.Bitmap
import com.chalkbox.propertyfinder.api.application.componentfactory.FirebaseComponentFactory
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class StorageApi {

    @Inject
    internal lateinit var firebaseStorage: FirebaseStorage


    init {
        FirebaseComponentFactory().newFirebaseStorageComponent().inject(this)
    }

    fun uploadImage(
        filename: String,
        bitmap: Bitmap,
        onProgressListener: (Int) -> Unit,
        onFailureListener: (String) -> Unit,
        onCompleteListener: (String) -> Unit
    ) {
        val storageRef = firebaseStorage.reference
        val imageRef = storageRef.child("$IMAGES_NAME/$filename")

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val data = stream.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnProgressListener {
            onProgressListener(1)
        }.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnFailureListener {
            // Handle unsuccessful uploads
            onFailureListener("ERROR")
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val string = downloadUri.toString()
                onCompleteListener(string)
//                onUploadListener.onUploadSuccessful(string)
            } else {
                // Handle failures
                // ...
            }
        }
    }

    fun uploadImages(
        filenamePrefix: String,
        bitmapList: List<Bitmap>,
        onProgressListener: (Long) -> Unit,
        onFailureListener: (String) -> Unit,
        onCompleteListener: (List<String>) -> Unit
    ) {
        val storageRef = firebaseStorage.reference

        var uploadCount = 0
        val imagesToUpload = bitmapList.size
        val urls = mutableListOf<String>()
        val totalProgress = LongArray(imagesToUpload)
        for ((index, bitmap) in bitmapList.withIndex()) {
            val imageRef = storageRef.child("$IMAGES_NAME/${filenamePrefix}_$index.jpg")
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            val data = stream.toByteArray()
            val uploadTask = imageRef.putBytes(data)

            // UPLOAD
            uploadTask.addOnProgressListener { taskSnapshot ->
                // Update progress
                val currentProgress = taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                totalProgress[index] = currentProgress * 100
                val actualProgress = totalProgress.sum() / imagesToUpload
                onProgressListener(actualProgress)
            }.addOnFailureListener {
                // Handle unsuccessful uploads
                onFailureListener("ERROR")
            }.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    uploadCount++
                    val downloadUri = task.result
                    val url = downloadUri.toString()
                    urls.add(url)
                    if (uploadCount >= imagesToUpload) {
                        onCompleteListener(urls)
                    }
                } else {
                    // Handle unsuccessful uploads
                    onFailureListener("ERROR")
                }
            }
        }
    }

    companion object {
        private const val IMAGES_NAME = "images"
    }
}