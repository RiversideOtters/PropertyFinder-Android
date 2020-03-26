package com.chalkbox.propertyfinder.feed.presenters.base

import android.view.ViewGroup
import com.chalkbox.propertyfinder.navigation.Navigator

interface GroupPresenterFacade {
    fun onPause()
    fun onResume()
    fun unbindView()
    fun bindView(container: ViewGroup)
    var navigator: Navigator?
}