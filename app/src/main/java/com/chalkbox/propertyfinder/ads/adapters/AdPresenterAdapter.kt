package com.chalkbox.propertyfinder.ads.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.ads.views.NativeTemplateStyle
import com.chalkbox.propertyfinder.base.adapters.PresenterAdapter
import com.chalkbox.propertyfinder.base.adapters.PresenterViewHolder
import com.chalkbox.propertyfinder.dto.pojos.Ad
import kotlinx.android.synthetic.main.card_view_feed_ad.view.*

class AdPresenterAdapter<T : Ad>(context: Context) : PresenterAdapter<T>(context) {
    override val layout: Int = R.layout.card_view_feed_ad

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<T> {
        val view = layoutInflater.inflate(layout, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener(viewHolder)
        return viewHolder
    }

    inner class ViewHolder(private val view: View) : PresenterViewHolder<T>(view) {
        override fun setItem(item: T) {
            val background = ColorDrawable(Color.parseColor("#ffffff"))
            val styles = NativeTemplateStyle.Builder().withMainBackgroundColor(background).build()
            val template = view.ad_view
            template.setStyles(styles)
            template.setNativeAd(item.unifiedNativeAd)
        }
    }
}