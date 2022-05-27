package com.pakollya.moviecollection.presentation.detail

import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.presentation.base.BaseContract

interface DetailContract {

    interface View : BaseContract.View {
        fun showMovie(movie: Movie)
    }

    interface Presenter: BaseContract.Presenter<View?> {
        fun getMovie(title: String)
    }
}