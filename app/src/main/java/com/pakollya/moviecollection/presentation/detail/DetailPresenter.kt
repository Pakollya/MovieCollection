@file:OptIn(ExperimentalPagingApi::class)

package com.pakollya.moviecollection.presentation.detail

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.base.BasePresenter

class DetailPresenter (
    private val movieInteractor: MovieInteractor
) : BasePresenter<DetailContract.View>(), DetailContract.Presenter
{
    override fun movieByTitle(title: String) {
        val disposable = movieInteractor.movieByTitle(title)
            .subscribe{ movie ->
                view?.showMovie(movie)
            }
        compositeDisposable?.add(disposable)
    }
}