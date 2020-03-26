package com.chalkbox.propertyfinder.property.details

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.presenters.Presenter
import com.chalkbox.propertyfinder.dto.pojoFormatters.PropertyFormatter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.chalkbox.propertyfinder.dto.pojos.User
import com.chalkbox.propertyfinder.feed.presenters.base.GroupPresenterFacade
import com.chalkbox.propertyfinder.navigation.Navigator
import com.chalkbox.propertyfinder.property.details.content.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_property_details.view.*
import kotlinx.android.synthetic.main.property_details_bottom_bar.view.*


class PropertyDetailsPresenter(
    private val activity: FragmentActivity,
    private val property: Property,
    picasso: Picasso,
    override var navigator: Navigator?
) : GroupPresenterFacade {

    private lateinit var contactOwnerButton: Button

    private val presenters: List<Presenter<View>>

    private var mainContent: PropertyBasicInfoPresenter

    private var user: User? = null

    init {
        this.presenters = mutableListOf()

        mainContent =
            PropertyBasicInfoPresenter(
                identifier = "MainContent",
                sortOrder = 0,
                property = property,
                picasso = picasso
            )
        val images = PropertyImagesPresenter(
            context = activity,
            identifier = "Images",
            sortOrder = -5,
            picasso = picasso,
            imageUrls = property.images
        )
        val extraInfo =
            PropertyExtraInfoPresenter(
                context = activity,
                identifier = "Extra Info",
                sortOrder = 5,
                property = property
            )
        val amenities =
            PropertyAmenitiesPresenter(
                context = activity,
                identifier = "",
                sortOrder = 0,
                amenities = property.amenities
            )
        val otherDetails =
            PropertyOtherDetailsPresenter(
                context = activity,
                identifier = "Extra Info",
                sortOrder = 10,
                settings = property.settings
            )
        val location =
            PropertyLocationPresenter(
                context = activity,
                fragmentManager = activity.supportFragmentManager,
                identifier = "Location",
                sortOrder = 15,
                property = property
            )

        this.presenters.addAll(
            listOf(
                images,
                mainContent,
                extraInfo,
                otherDetails,
                amenities,
                location
            )
        )
    }

    fun setUser(user: User) {
        this.user = user
        mainContent.setUser(user)
        contactOwnerButton.visibility = View.VISIBLE
        contactOwnerButton.setOnClickListener {
            sendMessage(user)
        }
    }

    override fun bindView(container: ViewGroup) {
        // Contact
        contactOwnerButton = container.propertyContactButton
        contactOwnerButton.visibility = View.GONE


        // Price
        val propertyFormatter = PropertyFormatter(property)
        if (propertyFormatter.displayMonthlyPrice.isNotEmpty()) {
            container.propertyPrice.text = propertyFormatter.displayMonthlyPrice
            container.propertyPriceType.text = activity.getText(R.string.price_per_month_suffix)
        } else {
            container.propertyPrice.text = activity.getText(R.string.inquire_price)
            container.propertyPriceType.text = ""
        }

        val inflater = LayoutInflater.from(container.context)
        for (presenter in presenters) {
            val view = inflater.inflate(presenter.resId, null, false)
            presenter.bindView(view)
            container.propertyDetailsContainer.addView(view)
        }
    }

    override fun onPause() {}

    override fun onResume() {}

    override fun unbindView() {
        for (presenter in presenters) {
            presenter.unbindView()
        }
    }

    private fun sendMessage(user: User) {
        val smsIntent = Intent(Intent.ACTION_SENDTO)
        smsIntent.data = Uri.parse(user.phone)
        smsIntent.putExtra("sms_body", "Hi ${user.name}!\nI'm interested in: ${property.name}\nThanks!")
        if (smsIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(smsIntent);
        }
    }
}