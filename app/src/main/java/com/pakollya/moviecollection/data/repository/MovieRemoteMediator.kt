package com.pakollya.moviecollection.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.pakollya.moviecollection.API_KEY
import com.pakollya.moviecollection.INITIAL_LOAD_OFFSET
import com.pakollya.moviecollection.NETWORK_PAGE_SIZE
import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.data.database.AppDatabase
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.database.entity.MovieRemoteKey
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MovieRemoteMediator(val apiService: MovieApiService, val database: AppDatabase): RxRemoteMediator<Int, Movie>() {

    override fun loadSingle(
        loadType: LoadType,
         state: PagingState<Int, Movie>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                Log.e("LoadType", "$it")
                when(it) {
                    LoadType.REFRESH -> {
                        val remoteKey = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKey?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKey = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        Log.e("PREPEND", "$remoteKey")

                        remoteKey.prevKey ?: -1
                    }
                    LoadType.APPEND -> {
                        val remoteKey = getRemoteKeyForLastItem(state)

                        Log.e("APPEND", "$remoteKey")

                        remoteKey?.nextKey ?: 2
                    }
                }
            }
            .flatMap { page ->
                Log.e("Page", "$page")
                if (page == -1) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                }  else {
                    val offset = if(page == 1) INITIAL_LOAD_OFFSET else page * NETWORK_PAGE_SIZE
                    val movies = mutableListOf<Movie>()
                    apiService.getAllMovies(API_KEY, offset).map { movieResponse ->
                            movieResponse.body()
                                ?.let {
                                    insertMoviesToDB(page, loadType, it.results, state)
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

    private fun insertMoviesToDB(
        page: Int,
        loadType: LoadType,
        data: List<Movie>,
        state: PagingState<Int, Movie>
    ): List<Movie> {
        val movies = data
        when(loadType) {
            LoadType.REFRESH -> {
                database.movieRemoteKeysDao().clearAllKeys()
                database.moviesDao().clearAllMovies()

                movies.forEachIndexed { index, movie ->
                    movie.id = index.toLong()
                }
            }
            LoadType.PREPEND -> {
                movies.map {
                    val databaseId = getFirstBlogDatabaseId(state)?.id ?: 0
                    movies.forEachIndexed { index, movie ->
                        movie.id = databaseId - (movies.size - index.toLong())
                    }
                }
            }
            LoadType.APPEND -> {
                val databaseId = getLastBlogDatabaseId(state)?.id ?: 0
                movies.forEachIndexed { index, movie ->
                    movie.id = databaseId + index.toLong() + 1
                }
            }
        }

        val prevKey = if (page == 1) null else page - 1
        val nextKey = page + 1
        val keys = movies.map {
            MovieRemoteKey(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
        }
        database.movieRemoteKeysDao().insertAllKeys(keys)
        database.moviesDao().insertAllMovies(movies)

        return movies
    }

    private fun getFirstBlogDatabaseId(state: PagingState<Int, Movie>): Movie? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
    }

    private fun getLastBlogDatabaseId(state: PagingState<Int, Movie>): Movie? {
        return state.lastItemOrNull()
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { movie ->
            database.movieRemoteKeysDao().getKeyByMovieId(movie.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.movieRemoteKeysDao().getKeyByMovieId(movie.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.movieRemoteKeysDao().getKeyByMovieId(id)
            }
        }
    }
}