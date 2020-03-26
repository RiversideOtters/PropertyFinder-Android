package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.scopes.FeedPresenterScope
import com.chalkbox.propertyfinder.feed.presenters.FeedSuggestionsPresenter
import dagger.Component

@FeedPresenterScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface FeedPresenterComponent {
    fun inject(arg: FeedSuggestionsPresenter)
}