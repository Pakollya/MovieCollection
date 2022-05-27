package com.pakollya.moviecollection.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.MovieItemBinding

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, clickListener: MovieItemClickListener<Movie>) {
        binding.movie = movie
        binding.movieItem.setOnClickListener {
            clickListener.openDetail(movie)
        }
    }

    fun unbind() {
        binding.unbind()
    }
}