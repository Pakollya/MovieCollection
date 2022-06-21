package com.pakollya.moviecollection.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.pakollya.moviecollection.presentation.adapter.LoaderStateAdapter.State.*
import com.pakollya.moviecollection.presentation.adapter.viewholder.ErrorViewHolder
import com.pakollya.moviecollection.presentation.adapter.viewholder.ItemViewHolder
import com.pakollya.moviecollection.presentation.adapter.viewholder.ProgressViewHolder

class LoaderStateAdapter : LoadStateAdapter<ItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS.id
        is LoadState.Error -> ERROR.id
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when(loadState) {
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ErrorViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    enum class State(val id: Int) {
        PROGRESS(0),
        ERROR(1),
    }
}