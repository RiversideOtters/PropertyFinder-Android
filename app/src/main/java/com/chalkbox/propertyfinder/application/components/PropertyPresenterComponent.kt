package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.scopes.PropertyPresenterScope
import com.chalkbox.propertyfinder.feed.presenters.FeedPropertyPresenter
import dagger.Component

@PropertyPresenterScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface PropertyPresenterComponent {
    fun inject(arg: FeedPropertyPresenter)
}