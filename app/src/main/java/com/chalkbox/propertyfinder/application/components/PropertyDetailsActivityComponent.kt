package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.scopes.PropertyDetailsActivityScope
import com.chalkbox.propertyfinder.property.details.PropertyDetailsActivity
import dagger.Component

@PropertyDetailsActivityScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface PropertyDetailsActivityComponent {
    fun inject(arg: PropertyDetailsActivity)
}