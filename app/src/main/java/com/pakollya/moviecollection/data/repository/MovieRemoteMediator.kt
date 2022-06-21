package com.pakollya.moviecollection.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.pakollya.moviecollection.utils.NETWORK_PAGE_SIZE
import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.data.database.CacheDataSource.BaseDataSource
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.database.entity.MovieRemoteKey
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRemoteMediator @Inject constructor (
    private val apiService: MovieApiService,
    private val database: BaseDataSource
): RxRemoteMediator<Int, Movie>() {

    override fun loadSingle(
        loadType: LoadType,
         state: PagingState<Int, Movie>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when(it) {
                    LoadType.REFRESH -> {
                        val remoteKey = remoteKeyClosestToCurrentPosition(state)

                        remoteKey?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKey = remoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKey.prevKey ?: -1
                    }
                    LoadType.APPEND -> {
                        val remoteKey = remoteKeyForLastItem(state)

                        remoteKey?.nextKey ?: 1
                    }
                }
            }
            .flatMap { page ->
                Log.e("Page", "$page")
                if (page == -1) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                }  else {
                    val offset = (page -1) * NETWORK_PAGE_SIZE
                    apiService.movieApiResponse(offset).map { movieResponse ->
                            movieResponse.body()
                                ?.let {
                                    insertMovieList(page, loadType, it.results, state)
                                }
                    }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
                        .onErrorReturn {
                            MediatorResult.Error(it)
                        }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }
    }

    private fun insertMovieList(
        page: Int,
        loadType: LoadType,
        data: List<Movie>,
        state: PagingState<Int, Movie>
    ): List<Movie> {
        val listMovie: List<Movie> = data
        when(loadType) {
            LoadType.REFRESH -> {
                database.movieRemoteKeyDao().clearListKey()
                database.movieDao().clearListMovie()

                listMovie.forEachIndexed { index, movie ->
                    movie.id = index.toLong()
                }
            }
            LoadType.PREPEND -> {
                listMovie.map {
                    val databaseId = firstBlogDatabaseId(state)?.id ?: 0
                    listMovie.forEachIndexed { index, movie ->
                        movie.id = databaseId - (listMovie.size - index.toLong())
                    }
                }
            }
            LoadType.APPEND -> {
                val databaseId = lastBlogDatabaseId(state)?.id ?: 0
                listMovie.forEachIndexed { index, movie ->
                    movie.id = databaseId + index.toLong() + 1
                }
            }
        }

        val prevKey = if (page == 1) null else page - 1
        val nextKey = page + 1
        val keys = listMovie.map {
            MovieRemoteKey(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
        }
        database.movieRemoteKeyDao().insertListKeys(keys)
        database.movieDao().insertListMovie(listMovie)

        return listMovie
    }

    private fun firstBlogDatabaseId(state: PagingState<Int, Movie>): Movie? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
    }

    private fun lastBlogDatabaseId(state: PagingState<Int, Movie>): Movie? {
        return state.lastItemOrNull()
    }

    private fun remoteKeyForLastItem(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { movie ->
            database.movieRemoteKeyDao().keyByMovieId(movie.id)
        }
    }

    private fun remoteKeyForFirstItem(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.movieRemoteKeyDao().keyByMovieId(movie.id)
        }
    }

    private fun remoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.movieRemoteKeyDao().keyByMovieId(id)
            }
        }
    }
}