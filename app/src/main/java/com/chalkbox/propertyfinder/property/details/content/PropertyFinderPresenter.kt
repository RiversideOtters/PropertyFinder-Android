package com.chalkbox.propertyfinder.property.details.content

import android.view.LayoutInflater
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.presenters.Presenter
import kotlinx.android.synthetic.main.presenter_section_layout.view.*

abstract class PropertyFinderPresenter<T>(
    val identifier: String,
    val sortOrder: Int
) : Presenter<View>() {

    protected abstract val shouldShow: Boolean
    protected open val showDivider: Boolean = true

    protected open val heading: String = ""
    protected open val caption: String = ""
    protected abstract val contentResId: Int

    override val resId: Int = R.layout.presenter_section_layout

    override fun bindView(view: View) {
        super.bindView(view)

        view.sectionDivider.visibility = if (showDivider) {
            View.VISIBLE
        } else {
            View.GONE
        }

        view.visibility = if (shouldShow) {
            View.VISIBLE
        } else {
            View.GONE
        }

        if (heading.isNotEmpty()) {
            view.sectionHeader.text = heading
            view.sectionHeader.visibility = View.VISIBLE
        } else {
            view.sectionHeader.visibility = View.GONE
        }

        if (caption.isNotEmpty()) {
            view.sectionCaption.text = caption
            view.sectionCaption.visibility = View.VISIBLE
        } else {
            view.sectionCaption.visibility = View.GONE
        }

        val inflater = LayoutInflater.from(view.context)
        val contentView = inflater.inflate(contentResId, null, false)
        view.sectionContent.addView(contentView)
    }
}