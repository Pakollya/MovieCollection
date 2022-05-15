package com.pakollya.moviecollection.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.api.Movie
import com.pakollya.moviecollection.presentation.viewholder.MovieViewHolder

class MovieAdapter(context: Context, list: MutableList<Movie>): BaseAdapter<Movie, MovieViewHolder>(context, list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false
        ))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}