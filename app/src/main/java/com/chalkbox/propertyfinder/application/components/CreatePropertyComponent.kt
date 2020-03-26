package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.scopes.CreatePropertyScope
import com.chalkbox.propertyfinder.property.create.PropertyCreateDetailsActivity
import com.chalkbox.propertyfinder.property.create.PropertyCreateImageAndCountryActivity
import com.chalkbox.propertyfinder.property.create.country.CountrySelectorPresenter
import com.chalkbox.propertyfinder.property.create.PropertyUserCreateActivity
import dagger.Component

@CreatePropertyScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface CreatePropertyComponent {
    fun inject(arg: CountrySelectorPresenter)
    fun inject(arg: PropertyCreateImageAndCountryActivity)
    fun inject(arg: PropertyCreateDetailsActivity)
    fun inject(arg: PropertyUserCreateActivity)
}