package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.MainActivity
import com.chalkbox.propertyfinder.application.scopes.MainActivityScope
import dagger.Component

@MainActivityScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface MainActivityComponent {
    fun inject(arg: MainActivity)
}