package com.chalkbox.propertyfinder

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.feed.presenters.base.GroupPresenterFacade
import com.chalkbox.propertyfinder.navigation.Navigator
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var groupPresenterFacade: GroupPresenterFacade

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        AppComponentFactory()
            .newMainActivityComponent(applicationContext as Application)
            .inject(this)

        groupPresenterFacade.bindView(card_container)
        groupPresenterFacade.navigator = Navigator(this)
    }

    override fun onResume() {
        super.onResume()
        groupPresenterFacade.onResume()
    }

    override fun onPause() {
        super.onPause()
        groupPresenterFacade.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
