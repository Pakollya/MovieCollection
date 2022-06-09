package com.pakollya.moviecollection.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.pakollya.moviecollection.data.database.AppDatabase
import com.pakollya.moviecollection.data.database.entity.Movie
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@ExperimentalPagingApi
class MovieListRepository @Inject constructor (val database: AppDatabase, val remoteMediator: MovieRemoteMediator) {

    fun getMovies(): Flowable<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
//                initialLoadSize = 30,
                pageSize = 20,
                enablePlaceholders = false,
//                prefetchDistance = 5
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.moviesDao().getAllPagingMovie() }
        ).flowable

    fun getMovieByTitle(title: String): Single<Movie> = database.moviesDao().getMovieByTitle(title)
}