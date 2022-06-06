package com.pakollya.moviecollection.data.repository

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.pakollya.moviecollection.API_KEY
import com.pakollya.moviecollection.INITIAL_LOAD_OFFSET
import com.pakollya.moviecollection.NETWORK_PAGE_SIZE
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.api.MovieApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val apiService: MovieApiService): RxPagingSource<Int, Movie>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val position = params.key ?: INITIAL_LOAD_OFFSET
        val offset = if (params.key != null) position * NETWORK_PAGE_SIZE else INITIAL_LOAD_OFFSET
        return apiService.getAllMovies(API_KEY, offset)
            .subscribeOn(Schedulers.io())
            .flatMap { movieResponse ->
                return@flatMap if (movieResponse.isSuccessful) {
                    Single.just(movieResponse.body()?.let {
                        LoadResult.Page(
                            data = it.results,
                            prevKey = if (position == INITIAL_LOAD_OFFSET) null else position,
                            nextKey = if (it.results.isEmpty()) null else position + (params.loadSize / NETWORK_PAGE_SIZE)
                        )
                    })
                } else {
                    Single.error(Exception("Network error code: ${movieResponse.code()}"))
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}