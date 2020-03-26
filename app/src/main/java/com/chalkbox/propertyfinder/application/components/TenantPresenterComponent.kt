package com.chalkbox.propertyfinder.application.components

import com.chalkbox.propertyfinder.application.scopes.TenantPresenterScope
import com.chalkbox.propertyfinder.feed.presenters.FeedTenantPresenter
import dagger.Component

@TenantPresenterScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface TenantPresenterComponent {
    fun inject(arg: FeedTenantPresenter)
}