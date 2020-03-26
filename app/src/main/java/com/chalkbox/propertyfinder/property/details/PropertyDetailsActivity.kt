package com.chalkbox.propertyfinder.property.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.api.firebase.database.UserApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.intents.IntentFactory.Companion.PROPERTY_DETAILS_EXTRA
import com.chalkbox.propertyfinder.navigation.Navigator
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PropertyDetailsActivity : AppCompatActivity() {

    @Inject
    internal lateinit var picasso: Picasso

    @Inject
    internal lateinit var userApi: UserApi

    @Inject
    internal lateinit var propertyHolder: PropertyHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_property_details)

        AppComponentFactory()
            .newPropertyDetailsActivityComponent(application)
            .inject(this)

        val property = propertyHolder.property

        val presenter = PropertyDetailsPresenter(
            activity = this,
            property = property,
            picasso = picasso,
            navigator = Navigator(this)
        )
        presenter.bindView(findViewById(R.id.layout))

        property.user?.let { ref ->
            userApi.get(ref) { user ->
                presenter.setUser(user)
            }
        }
    }
}