package com.pakollya.moviecollection.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.repository.MovieListRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@ExperimentalPagingApi
class MovieInteractor(private val repository: MovieListRepository) {
    fun getMovies(): Flowable<PagingData<Movie>> = repository
        .getMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}