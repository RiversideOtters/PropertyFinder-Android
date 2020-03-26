package com.chalkbox.propertyfinder.feed.presenters.base

import android.util.Log
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.navigation.Navigator
import com.chalkbox.propertyfinder.property.list.PropertyListActivity
import com.chalkbox.propertyfinder.api.firebase.database.DatabaseApi
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.presenters.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.presenter_feed_layout.view.*

abstract class DatabaseApiPresenter<T>(
    val identifier: String,
    val sortOrder: Int,
    val adapter: PresenterAdapter<T>
) : Presenter<View>(), PresenterAdapter.Listener<T> {

    protected open val heading: String = ""
    protected open val subHeading: String = ""
    protected open val showAllTitle: String = ""

    private var feedNavigator: Navigator? = null
    var navigator: Navigator?
        get() = feedNavigator
        set(value) {
            feedNavigator = value
        }

    abstract val api: DatabaseApi<T>?

    override val resId: Int = R.layout.presenter_feed_layout
    private val compositeDisposable = CompositeDisposable()

    override fun bindView(view: View) {
        super.bindView(view)

        view.recycler_view.adapter = adapter
        adapter.listener = this

        if (heading.isNotEmpty()) {
            view.heading.text = heading
        } else {
            view.heading.visibility = View.GONE
        }

        if (subHeading.isNotEmpty()) {
            view.subHeading.text = subHeading
        } else {
            view.subHeading.visibility = View.GONE
        }

        if (showAllTitle.isNotEmpty()) {
            view.showAllButton.text = showAllTitle
            view.showAllButton.setOnClickListener { onShowAllClick() }
        } else {
            view.showAllButton.visibility = View.GONE
        }

        load()
    }

    override fun unbindView() {
        super.unbindView()
        compositeDisposable.clear()
    }

    private fun load() {
        api?.let {
            val disposable = it.observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    onLoaded(list)
                }

            compositeDisposable.add(disposable)

            it.get()
        }
    }

    private fun onLoaded(list: List<T>) {
        adapter.updateList(list)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(item: T?) {
        Log.d("Presenter", "Item Click")
    }

    override fun onShowAllClick() {
        Log.d("Presenter", "Show ALL")
    }
}