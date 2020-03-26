package com.chalkbox.propertyfinder.navigation

import android.app.Activity
import android.content.Intent
import android.os.Parcelable

class Navigator(private val activity: Activity) {
    fun navigateTo(target: Class<*>, parcel: Map<String, Parcelable>? = null, flags: Int = 0) {
        val intent = Intent(activity, target)

        if (flags != 0) {
            intent.flags = flags
        }

        parcel?.let {
            for ((key, value) in parcel.iterator()) {
                intent.putExtra(key, value)
            }
        }

        activity.startActivity(intent)
    }
}