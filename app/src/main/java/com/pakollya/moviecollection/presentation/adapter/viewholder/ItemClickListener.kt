package com.pakollya.moviecollection.presentation.adapter.viewholder

interface ItemClickListener<M> {
    fun openDetail(item: M?)
}