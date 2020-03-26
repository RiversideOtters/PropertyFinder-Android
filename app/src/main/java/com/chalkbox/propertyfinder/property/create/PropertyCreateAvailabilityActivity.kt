package com.chalkbox.propertyfinder.property.create

import android.app.DatePickerDialog
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.property.create.details.UnitAvailabilityPresenter
import java.util.*


class PropertyCreateAvailabilityActivity : PropertyCreateActivity(),
    UnitAvailabilityPresenter.Listener {

    override val propertyApi: PropertyApi? = null

    override val nextActivity: Class<*> = PropertyCreateDetailsActivity::class.java

    private lateinit var unitAvailability: UnitAvailabilityPresenter

    override fun setupPresenters() {
        unitAvailability = UnitAvailabilityPresenter(
            context = this,
            identifier = "",
            sortOrder = 0,
            property = property,
            listener = this
        )

        presenters.addAll(listOf(unitAvailability))
    }

    override fun updateInfo() {
        presenters.forEach { (it as? CreatePresenter<*>)?.updateInfo() }
    }

    override fun showDatePicker() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this@PropertyCreateAvailabilityActivity,
            R.style.DatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                unitAvailability.setDate(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}