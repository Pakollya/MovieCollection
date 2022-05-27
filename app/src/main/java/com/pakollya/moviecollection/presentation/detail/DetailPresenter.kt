package com.pakollya.moviecollection.presentation.detail

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.base.BasePresenter
import com.pakollya.moviecollection.presentation.detail.DetailContract

@ExperimentalPagingApi
class DetailPresenter(private val movieInteractor: MovieInteractor):
    BasePresenter<DetailContract.View>(),
    DetailContract.Presenter
{
    override fun getMovie(title: String) {
        val disposable = movieInteractor.getMovieByTitle(title)
            .subscribe{ movie ->
                view?.showMovie(movie)
            }
        compositeDisposable?.add(disposable)
    }
}