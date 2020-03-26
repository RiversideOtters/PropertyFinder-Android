package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.feed.presenters.base.GroupPresenterFacade
import com.chalkbox.propertyfinder.feed.presenters.base.FeedPresenterFacadeImpl
import dagger.Module
import dagger.Provides

@Module
class FeedModule {
    @Provides
    fun feedPresenterFacade(facade: FeedPresenterFacadeImpl): GroupPresenterFacade = facade
}
