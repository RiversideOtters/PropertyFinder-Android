package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.scopes.PropertyListActivityScope
import com.chalkbox.propertyfinder.property.list.PropertyListActivity
import com.chalkbox.propertyfinder.property.list.PropertyListPresenter
import dagger.Component

@PropertyListActivityScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface PropertyListActivityComponent {
    fun inject(arg: PropertyListActivity)
    fun inject(arg: PropertyListPresenter)
}