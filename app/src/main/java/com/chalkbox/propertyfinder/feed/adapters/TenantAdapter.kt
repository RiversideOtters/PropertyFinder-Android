package com.chalkbox.propertyfinder.feed.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojos.Tenant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view_square.view.*
import java.util.*

class TenantAdapter<T : Tenant>(context: Context, private val picasso: Picasso) : PresenterAdapter<T>(context) {
    override val layout: Int = R.layout.card_view_square

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<T> {
        val view = layoutInflater.inflate(layout, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener(viewHolder)
        return viewHolder
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<T>(view) {
        override fun setItem(item: T) {
            item.name?.let { name ->
                view.cardViewTitle.text = name
            }
            item.img?.let { img ->
                picasso.load(img).into(view.cardViewImage)
            }
        }

        override fun onClick(v: View) {
            listener?.onItemClick(list[adapterPosition])
        }
    }
}