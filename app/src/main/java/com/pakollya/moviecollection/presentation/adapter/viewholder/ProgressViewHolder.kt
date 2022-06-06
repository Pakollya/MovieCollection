package com.pakollya.moviecollection.presentation.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.pakollya.moviecollection.databinding.ErrorItemBinding
import com.pakollya.moviecollection.databinding.ProgressItemBinding

abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(loadState: LoadState)
}

class ProgressViewHolder internal constructor(
    private val binding: ProgressItemBinding
) : ItemViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {
        // Do nothing
    }

    companion object {

        operator fun invoke(
            layoutInflater: LayoutInflater,
            parent: ViewGroup? = null,
            attachToRoot: Boolean = false
        ): ProgressViewHolder {
            return ProgressViewHolder(
                ProgressItemBinding.inflate(
                    layoutInflater,
                    parent,
                    attachToRoot
                )
            )
        }
    }
}

class ErrorViewHolder internal constructor(
    private val binding: ErrorItemBinding
) : ItemViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {
        require(loadState is LoadState.Error)
        binding.errorMessage.text = loadState.error.localizedMessage
    }

    companion object {

        operator fun invoke(
            layoutInflater: LayoutInflater,
            parent: ViewGroup? = null,
            attachToRoot: Boolean = false
        ): ErrorViewHolder {
            return ErrorViewHolder(
                ErrorItemBinding.inflate(
                    layoutInflater,
                    parent,
                    attachToRoot
                )
            )
        }
    }
}