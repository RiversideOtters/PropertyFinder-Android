package com.chalkbox.propertyfinder.base.presenters

import androidx.annotation.CallSuper

abstract class Presenter<VIEW_T> {

    abstract val resId: Int

    private var viewInternal: VIEW_T? = null
    protected open val view: VIEW_T?
        get() = viewInternal

    @CallSuper
    open fun bindView(view: VIEW_T) {
        this.viewInternal = view
    }

    @CallSuper
    open fun unbindView() {
        this.viewInternal = null
    }
}