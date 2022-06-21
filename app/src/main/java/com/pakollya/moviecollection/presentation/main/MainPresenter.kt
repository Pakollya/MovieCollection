@file:OptIn(ExperimentalPagingApi::class)

package com.pakollya.moviecollection.presentation.main

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.base.BasePresenter

class MainPresenter (
    private val movieInteractor: MovieInteractor
): BasePresenter<MainContract.View>(), MainContract.Presenter
{
    override fun listMovie() {
        val disposable = movieInteractor.listMovie()
            .subscribe{movieList ->
                view?.showListMovie(movieList)
            }
        compositeDisposable?.add(disposable)
    }
}