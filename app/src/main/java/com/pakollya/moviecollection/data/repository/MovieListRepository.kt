package com.pakollya.moviecollection.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.pakollya.moviecollection.NETWORK_PAGE_SIZE
import com.pakollya.moviecollection.data.database.AppDatabase
import com.pakollya.moviecollection.data.database.entity.Movie
import io.reactivex.Flowable
import io.reactivex.Single

@ExperimentalPagingApi
class MovieListRepository(val database: AppDatabase, val remoteMediator: MovieRemoteMediator) {

    fun getMovies(): Flowable<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                initialLoadSize = NETWORK_PAGE_SIZE,
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 1),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.moviesDao().getAllMovie() }
        ).flowable

    fun getMovieByTitle(title: String): Single<Movie> = database.moviesDao().getMovieByTitle(title)

}