package com.pakollya.moviecollection.presentation.main

import androidx.paging.PagingData
import com.pakollya.moviecollection.data.api.Movie
import com.pakollya.moviecollection.presentation.base.BaseContract

interface MainContract {

    interface View : BaseContract.View {
        fun showMovies(movies: PagingData<Movie>)
    }

    interface Presenter : BaseContract.Presenter<View?> {
        fun getMovies()
    }
}