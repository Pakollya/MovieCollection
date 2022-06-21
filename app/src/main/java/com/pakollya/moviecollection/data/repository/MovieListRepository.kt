package com.pakollya.moviecollection.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.pakollya.moviecollection.data.database.CacheDataSource.BaseDataSource
import com.pakollya.moviecollection.data.database.entity.Movie
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@ExperimentalPagingApi
class MovieListRepository @Inject constructor (
    private val database: BaseDataSource,
    private val remoteMediator: MovieRemoteMediator
) : Repository {

    override fun listMovie(): Flowable<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
//                initialLoadSize = 30,
                pageSize = 20,
                enablePlaceholders = false,
//                prefetchDistance = 5
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.movieDao().listPagingMovie() }
        ).flowable

    override fun movieByTitle(title: String): Single<Movie> = database.movieDao().movieByTitle(title)
}