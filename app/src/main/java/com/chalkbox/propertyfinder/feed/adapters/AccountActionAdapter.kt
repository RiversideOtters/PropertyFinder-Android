package com.chalkbox.propertyfinder.feed.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojos.User

class AccountActionAdapter<T : User>(context: Context) : PresenterAdapter<T>(context) {
    override val layout: Int = R.layout.card_view_feed_user_update_profile

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<T> {
        val cardLayout = if (list.isEmpty()) {
            R.layout.card_view_feed_user_login
        } else {
            R.layout.card_view_feed_user_update_profile
        }

        val view = layoutInflater.inflate(cardLayout, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener(viewHolder)
        return viewHolder
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<T>(view) {
        override fun setItem(item: T) {
//            item.name?.let { name ->
//                view.city_name.text = name[name.keys.first()]?.toUpperCase(Locale.getDefault())
//            }
//            item.img?.let { img ->
//                picasso.load(img).into(view.city_image)
//            }
        }

        override fun onClick(v: View) {
            if (list.size > adapterPosition) {
                listener?.onItemClick(list[adapterPosition])
            } else {
                listener?.onItemClick(null)
            }
        }
    }

    override fun getItemCount(): Int  = 1

    override fun onBindViewHolder(holder: PresenterViewHolder<T>, position: Int) {

    }
}