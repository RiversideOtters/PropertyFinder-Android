package com.chalkbox.propertyfinder.base.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class PresenterViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    abstract fun setItem(item: T)
    open fun clearItem() { }
    override fun onClick(v: View) { }
}

abstract class PresenterAdapter<T>(context: Context) : RecyclerView.Adapter<PresenterViewHolder<T>>() {

    abstract val layout: Int

    val list: MutableList<T> = mutableListOf()
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    open var listener: Listener<T>? = null

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder<T>

    override fun onBindViewHolder(holder: PresenterViewHolder<T>, position: Int) {
        if (list.size > position) {
            holder.setItem(list[position])
        } else {
            holder.clearItem()
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    fun updateList(data: List<T>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    interface Listener<T> {
        fun onItemClick(item: T?)
        fun onShowAllClick()
    }
}
