package com.chalkbox.propertyfinder.property.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.squareup.picasso.Picasso

class ListAdapter<T : Property, VIEW_T : ListViewHolder<T>>(
    context: Context,
    private val listener: Listener<T>? = null,
    options: FirestorePagingOptions<T>,
    private val picasso: Picasso
) :
    FirestorePagingAdapter<T, VIEW_T>(options) {
    private val layout = R.layout.card_view_property

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(viewHolder: VIEW_T, position: Int, item: T) {
        viewHolder.setItem(item)
        viewHolder.itemView.setOnClickListener { listener?.onItemClick(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIEW_T {
        val view = layoutInflater.inflate(layout, parent, false)
        val viewHolder = ListViewHolder<T>(view, picasso)
        return viewHolder as VIEW_T
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL, LoadingState.LOADING_MORE ->
                listener?.onLoadingStart()
            LoadingState.LOADED, LoadingState.ERROR, LoadingState.FINISHED ->
                listener?.onLoadingFinished()
        }
    }

    interface Listener<T> {
        fun onLoadingStart()
        fun onLoadingFinished()
        fun onItemClick(item: T)
    }
}