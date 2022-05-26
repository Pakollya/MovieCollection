package com.pakollya.moviecollection.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.MovieItemBinding

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movie = movie
    }

    fun unbind() {
        binding.unbind()
    }
}