package com.pakollya.moviecollection.presentation.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, K: RecyclerView.ViewHolder>
    (val context: Context, val list: MutableList<T>): RecyclerView.Adapter<K>() {

    override fun onBindViewHolder(holder: K, position: Int) {
        holder.itemView.tag = list[position]
    }

    fun getItem(position: Int) = list[position]

    override fun getItemCount() = list.size

    fun add(item: T) { list.add(item) }

    fun addAll(list: MutableList<T>) {
        list.forEach { add(it) }
    }

    fun removeItem(item: T){
        val position = list.indexOf(item)
        if (position > -1) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}