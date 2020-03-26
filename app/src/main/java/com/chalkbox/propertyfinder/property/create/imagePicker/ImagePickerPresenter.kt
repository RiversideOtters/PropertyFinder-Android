package com.chalkbox.propertyfinder.property.create.imagePicker

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import kotlinx.android.synthetic.main.presenter_feed_layout.view.*

class ImagePickerPresenter<T : Bitmap>(
    context: Context,
    identifier: String,
    sortOrder: Int,
    title: String = "",
    private val imageHolder: ImageHolder,
    private val listener: Listener<T>?
) : CreatePresenter<View>(identifier, sortOrder),
    PresenterAdapter.Listener<T> {
    val adapter = ImagePickerAdapter<T>(context)

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.property_create_image_picker

    override val heading: String = if (title.isNotEmpty()) {
        title
    } else {
        context.resources.getString(R.string.image_picker_header)
    }
    override val caption: String = context.resources.getString(R.string.image_picker_caption)

    override fun bindView(view: View) {
        super.bindView(view)

        view.recycler_view.adapter = adapter
        adapter.listener = this
    }

    fun addBitmap(bitmap: T) {
        adapter.addBitmap(bitmap)
    }

    override fun onItemClick(item: T?) {
        listener?.addImage()
    }

    override fun onShowAllClick() {

    }

    interface Listener<T> {
        fun addImage()
    }

    override fun updateInfo() {
        imageHolder.list.addAll(adapter.list)
    }

    override fun updateError(): CreatePresenterResult =
        if (adapter.list.isEmpty()) {
            CreatePresenterResult(
                false,
                "At least one image is required."
            )
        } else {
            CreatePresenterResult(true)
        }
}