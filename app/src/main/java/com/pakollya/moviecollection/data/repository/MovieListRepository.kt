package com.pakollya.moviecollection.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.pakollya.moviecollection.NETWORK_PAGE_SIZE
import com.pakollya.moviecollection.data.api.Movie
import io.reactivex.Flowable

class MovieListRepository(private val moviePagingSource: MoviePagingSource) {

    fun getMovies(): Flowable<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                initialLoadSize = NETWORK_PAGE_SIZE,
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 1),
            pagingSourceFactory = {moviePagingSource}
        ).flowable
}