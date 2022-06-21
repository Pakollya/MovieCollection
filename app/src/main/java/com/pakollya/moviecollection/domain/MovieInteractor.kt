package com.pakollya.moviecollection.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.repository.MovieListRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ExperimentalPagingApi
class MovieInteractor @Inject constructor (
    private val repository: MovieListRepository
) : Interactor {

    override fun listMovie(): Flowable<PagingData<Movie>> = repository
        .listMovie()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun movieByTitle(title: String): Single<Movie> = repository
        .movieByTitle(title)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}