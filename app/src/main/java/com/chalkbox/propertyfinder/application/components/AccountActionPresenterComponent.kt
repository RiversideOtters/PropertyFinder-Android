package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.scopes.AccountActionPresenterScope
import com.chalkbox.propertyfinder.feed.presenters.FeedAccountActionPresenter
import dagger.Component

@AccountActionPresenterScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface AccountActionPresenterComponent {
    fun inject(arg: FeedAccountActionPresenter)
}