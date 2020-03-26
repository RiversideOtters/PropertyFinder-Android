package com.chalkbox.propertyfinder.property.details.content

import com.chalkbox.propertyfinder.R

abstract class PropertyContentPresenter<T>(
    identifier: String,
    sortOrder: Int
) : PropertyFinderPresenter<T>(
    identifier,
    sortOrder
) {
    override val resId: Int = R.layout.presenter_section_layout
}