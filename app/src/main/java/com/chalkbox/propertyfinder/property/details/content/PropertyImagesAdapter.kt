package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.property_image_view_holder.view.*

class PropertyImagesAdapter<T : String>(
    context: Context,
    private val picasso: Picasso,
    imageUrls: List<String>
) : PresenterAdapter<String>(context) {
    override val layout: Int = R.layout.property_image_view_holder

    init {
        list.addAll(imageUrls)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<String> {
        val view = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<String>(view) {
        override fun setItem(item: String) {
            if (item.isNotEmpty())
                picasso.load(item).into(view.propertyImage)
        }

        override fun onClick(v: View) {}
    }
}