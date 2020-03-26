package com.chalkbox.propertyfinder.property.create.country

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojoFormatters.RegionFormatter
import com.chalkbox.propertyfinder.dto.pojos.Region
import kotlinx.android.synthetic.main.county_selector_view_holder.view.*

class RegionSelectorAdapter<T : Region>(context: Context) :
    PresenterAdapter<T>(context) {
    override val layout: Int = R.layout.county_selector_view_holder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<T> {
        val view = layoutInflater.inflate(layout, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener(viewHolder)
        return viewHolder
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<T>(view) {
        override fun setItem(item: T) {
            val formatter = RegionFormatter(item)
            view.cardViewTitle.text = formatter.displayName
        }

        override fun onClick(v: View) {
            listener?.onItemClick(list[adapterPosition])
        }
    }
}