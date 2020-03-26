package com.chalkbox.propertyfinder.feed.presenters

import android.app.Application
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.dto.managers.UserAccountManager
import com.chalkbox.propertyfinder.feed.presenters.base.DatabaseApiPresenter
import com.chalkbox.propertyfinder.dto.pojos.User
import com.chalkbox.propertyfinder.intents.IntentFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FeedAccountActionPresenter(
    private val application: Application,
    identifier: String,
    sortOrder: Int,
    adapter: PresenterAdapter<User>
) : DatabaseApiPresenter<User>(identifier, sortOrder, adapter) {
    @Inject
    internal lateinit var userAccountManager: UserAccountManager
    @Inject
    internal lateinit var intentFactory: IntentFactory

    override val api: DatabaseApi<User>? = null

    private val compositeDisposable = CompositeDisposable()

    init {
        AppComponentFactory()
            .newAccountActionPresenterComponent(application)
            .inject(this)

        subscribeToAuth()
    }

    private fun subscribeToAuth() {
        val disposable = userAccountManager.observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                if (user != User.NULL_USER) {
                    adapter.list.clear()
                    adapter.list.add(user)
                    adapter.notifyDataSetChanged()
                }
            }
        compositeDisposable.add(disposable)
    }

    override fun onItemClick(item: User?) {
        if (item == null) {
            application.startActivity(intentFactory.newAuthSignUpIntent(application))
        } else {

        }
    }
}