@file:OptIn(ExperimentalPagingApi::class)

package com.pakollya.moviecollection.presentation.main

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.base.BasePresenter
import javax.inject.Inject

class MainPresenter (private val movieInteractor: MovieInteractor):
    BasePresenter<MainContract.View>(),
    MainContract.Presenter
{
    override fun getMovies() {
        val disposable = movieInteractor.getMovies()
            .subscribe{movieList ->
                view?.showMovies(movieList)
            }
        compositeDisposable?.add(disposable)
    }
}