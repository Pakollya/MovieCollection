package com.pakollya.moviecollection.presentation.main

import com.pakollya.moviecollection.data.api.Movie
import com.pakollya.moviecollection.presentation.BaseContract

interface MainContract {

    interface View : BaseContract.View {
        fun showMovies(movies: List<Movie>)
    }

    interface Presenter : BaseContract.Presenter<View?> {
        fun getMovies()
    }
}