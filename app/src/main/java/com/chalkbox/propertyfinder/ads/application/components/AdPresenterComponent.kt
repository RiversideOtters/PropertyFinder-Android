package com.chalkbox.propertyfinder.ads.application.components

import com.chalkbox.propertyfinder.ads.application.scopes.AdScope
import com.chalkbox.propertyfinder.ads.presenters.AdPresenter
import com.chalkbox.propertyfinder.application.components.BaseComponent
import dagger.Component

@AdScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface AdPresenterComponent {
    fun inject(arg: AdPresenter)
}