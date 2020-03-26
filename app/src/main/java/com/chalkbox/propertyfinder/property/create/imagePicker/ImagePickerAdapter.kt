package com.chalkbox.propertyfinder.property.create.imagePicker

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import kotlinx.android.synthetic.main.card_image_picker.view.*

class ImagePickerAdapter<T : Bitmap>(context: Context) : PresenterAdapter<T>(context) {

    private val placeholder = context.resources.getDrawable(R.drawable.ic_add)
    override val layout: Int = R.layout.card_image_picker

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<T> {
        val view = layoutInflater.inflate(layout, parent, false)
        val viewHolder = ViewHolder(view)
        view.imageDeleteButton.setOnClickListener { viewHolder.onDelete() }
        view.setOnClickListener { viewHolder.onAdd() }
        return viewHolder
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<T>(view) {
        override fun setItem(item: T) {
            view.imagePickerBitmap.setImageBitmap(item)
            view.imageDeleteButton.visibility = View.VISIBLE
        }

        override fun clearItem() {
            view.imagePickerBitmap.setImageDrawable(placeholder)
            view.imageDeleteButton.visibility = View.GONE
        }

        fun onAdd() = listener?.onItemClick(null)

        fun onDelete() {
            if (adapterPosition < list.size) {
                list.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = NUMBER_OF_IMAGES

    fun addBitmap(bitmap: T) {
        list.add(bitmap)
        notifyDataSetChanged()
    }

    companion object {
        const val NUMBER_OF_IMAGES = 5
    }
}