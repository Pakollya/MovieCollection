package com.pakollya.moviecollection.domain

import com.pakollya.moviecollection.API_KEY
import com.pakollya.moviecollection.data.api.Movie
import com.pakollya.moviecollection.data.repository.MovieListRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieInteractor(private val repository: MovieListRepository) {
    fun getMovies(offset: Int = 0): Single<List<Movie>> = repository
        .getMovies(API_KEY, offset)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}