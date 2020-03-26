package com.chalkbox.propertyfinder.property.create

import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.dto.pojos.Region
import com.chalkbox.propertyfinder.property.create.country.RegionSelectorPresenter

class PropertyCreateRegionActivity : PropertyCreateActivity(),
    RegionSelectorPresenter.Listener<Region> {

    override val nextButtonTitle: String = ""

    override val propertyApi: PropertyApi? = null

    override val nextActivity: Class<*> = PropertyCreateAvailabilityActivity::class.java

    private lateinit var regionSelectorPresenter: RegionSelectorPresenter

    override fun setupPresenters() {
        regionSelectorPresenter = RegionSelectorPresenter(
            context = this,
            identifier = "",
            sortOrder = 0,
            property = property,
            regions = country?.regions ?: listOf(),
            listener = this
        )

        presenters.addAll(listOf(regionSelectorPresenter))
    }

    override fun updateInfo() {
        presenters.forEach { (it as? CreatePresenter<*>)?.updateInfo() }
    }

    override fun pickRegion(item: Region) {
        next()
    }
}