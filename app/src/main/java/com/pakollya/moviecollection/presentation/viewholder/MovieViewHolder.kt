package com.pakollya.moviecollection.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pakollya.moviecollection.data.api.Movie
import com.pakollya.moviecollection.databinding.MovieItemBinding

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movie = movie
    }

    fun unbind() {
        binding.unbind()
    }
}