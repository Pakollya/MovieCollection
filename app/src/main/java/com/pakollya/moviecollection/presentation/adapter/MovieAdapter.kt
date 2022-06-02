package com.pakollya.moviecollection.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.presentation.adapter.viewholder.MovieItemClickListener
import com.pakollya.moviecollection.presentation.adapter.viewholder.MovieViewHolder

class MovieAdapter: PagingDataAdapter<Movie, MovieViewHolder>(DIFF_UTIL) {

    private lateinit var itemClickListener: MovieItemClickListener<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false
        ))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, itemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.movie_item
    }

    fun setItemClickListener(listener: MovieItemClickListener<Movie>) {
        itemClickListener = listener
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}