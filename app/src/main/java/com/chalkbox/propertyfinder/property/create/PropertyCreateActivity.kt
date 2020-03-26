package com.chalkbox.propertyfinder.property.create

import android.os.Parcelable
import com.chalkbox.propertyfinder.activities.CreateActivity
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.dto.pojos.Country
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.intents.IntentFactory

abstract class PropertyCreateActivity : CreateActivity() {

    protected lateinit var property: Property
    protected var country: Country? = null

    abstract val propertyApi: PropertyApi?

    override fun setupIntentExtras() {
        property = if (intent.hasExtra(IntentFactory.PROPERTY_CREATE_PROPERTY_EXTRA)) {
            intent.getParcelableExtra(IntentFactory.PROPERTY_CREATE_PROPERTY_EXTRA)
        } else {
            Property()
        }
        if (intent.hasExtra(IntentFactory.PROPERTY_CREATE_COUNTRY_EXTRA)) {
            country = intent.getParcelableExtra(IntentFactory.PROPERTY_CREATE_COUNTRY_EXTRA)
        }
    }

    override fun create() {
        propertyApi?.let { api ->
            if (!checkError()) {
                return
            }

            for (presenter in presenters) {
                (presenter as? CreatePresenter<*>)?.updateInfo()
            }

            showAd()

            api.create(property) { propertyId, propertyRef ->
                postCreate(
                    mapOf(
                        PROPERTY_ID_KEY to propertyId,
                        PROPERTY_DOCU_REF_KEY to propertyRef
                    )
                )

                finishAffinity()
            }
        }
    }

    override val nextActivityParcel: Map<String, Parcelable>
        get() = country?.let {
            mapOf<String, Parcelable>(
                IntentFactory.PROPERTY_CREATE_PROPERTY_EXTRA to property,
                IntentFactory.PROPERTY_CREATE_COUNTRY_EXTRA to it
            )
        } ?: run {
            mapOf<String, Parcelable>(
                IntentFactory.PROPERTY_CREATE_PROPERTY_EXTRA to property
            )
        }

    companion object {
        const val PROPERTY_ID_KEY = "PROPERTY_ID"
        const val PROPERTY_DOCU_REF_KEY = "PROPERTY_DOCU_REF"
        const val ACCOUNT_ID_KEY = "ACCOUNT_ID"
        const val ACCOUNT_DOCU_REF_KEY = "ACCOUNT_DOCU_REF"
    }
}