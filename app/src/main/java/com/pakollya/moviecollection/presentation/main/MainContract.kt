package com.pakollya.moviecollection.presentation.main

import androidx.paging.PagingData
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.presentation.base.BaseContract

interface MainContract {

    interface View : BaseContract.View {
        fun showListMovie(listMovie: PagingData<Movie>)
    }

    interface Presenter : BaseContract.Presenter<View?> {
        fun listMovie()
    }
}