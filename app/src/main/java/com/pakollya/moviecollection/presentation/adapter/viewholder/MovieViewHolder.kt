package com.pakollya.moviecollection.presentation.adapter.viewholder

import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.MovieItemBinding

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val itemClickListener: ItemClickListener<Movie>
) : BaseViewHolder<MovieItemBinding, Movie>(binding) {

    override fun onBind(movie: Movie) {
        binding.movie = movie
        binding.movieItem.setOnClickListener {
            itemClickListener.openDetail(movie)
        }
    }
}