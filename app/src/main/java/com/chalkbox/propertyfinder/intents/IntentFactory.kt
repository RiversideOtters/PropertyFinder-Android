package com.chalkbox.propertyfinder.intents

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.chalkbox.propertyfinder.auth.activity.AuthSignUpActivity
import javax.inject.Inject

class IntentFactory @Inject constructor() {
    fun newAuthSignUpIntent(context: Context): Intent {
        val intent = Intent(context, AuthSignUpActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        return intent
    }

    companion object {
        const val PROPERTY_DETAILS_EXTRA = "property"
        const val PROPERTY_CREATE_PROPERTY_EXTRA = "new_property"
        const val PROPERTY_CREATE_COUNTRY_EXTRA = "country"
    }
}