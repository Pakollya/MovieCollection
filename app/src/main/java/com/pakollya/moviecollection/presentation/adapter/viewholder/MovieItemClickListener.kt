package com.pakollya.moviecollection.presentation.adapter.viewholder

interface MovieItemClickListener<M> {
    fun openDetail(movie: M)
}