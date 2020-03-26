package com.chalkbox.propertyfinder.activities

import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.property.details.content.PropertyFinderPresenter

class CreatePresenterResult(val result: Boolean, val description: String = "")

abstract class CreatePresenter<T>(
    identifier: String,
    sortOrder: Int,
    private val mutable: Boolean = true
) : PropertyFinderPresenter<T>(
    identifier,
    sortOrder
) {
    override val resId: Int = R.layout.presenter_section_layout

    abstract fun updateInfo()
    abstract fun updateError(): CreatePresenterResult
}