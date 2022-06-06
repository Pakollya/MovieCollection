package com.pakollya.moviecollection.presentation.adapter.animation

import androidx.recyclerview.widget.RecyclerView

class SlideInLeftAnimator: CommonItemAnimator {

    override fun animateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().translationX(-holder.itemView.rootView.width.toFloat())
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().translationX(0f)
    }

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationX = -holder.itemView.rootView.width.toFloat()
    }

}