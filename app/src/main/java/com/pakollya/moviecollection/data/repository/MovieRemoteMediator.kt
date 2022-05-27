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

                        remoteKey?.nextKey ?: NETWORK_PAGE_SIZE
                    }
                }
            }
            .flatMap { page ->
                Log.e("Page", "$page")
                if (page == -1) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                }  else {
                    val offset = if(page == 1) INITIAL_LOAD_OFFSET else page
                    apiService.getAllMovies(API_KEY, offset).map { movieResponse ->
                            movieResponse.body()
                                ?.let { insertMoviesToDB(offset, loadType, it.results) }
                    }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
                        .onErrorReturn {
                            MediatorResult.Error(it)
                        }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }

    }

    private fun insertMoviesToDB(offset: Int, loadType: LoadType, data: List<Movie>): List<Movie> {
        if (loadType == LoadType.REFRESH){
            database.movieRemoteKeysDao().clearAllKeys()
            database.moviesDao().clearAllMovies()
        }

        val prevKey = if (offset == 0) null else offset - NETWORK_PAGE_SIZE
        val nextKey = offset + NETWORK_PAGE_SIZE
        val keys = data.map {
            MovieRemoteKey(movieName = it.title, prevKey = prevKey, nextKey = nextKey)
        }
        database.movieRemoteKeysDao().insertAllKeys(keys)
        database.moviesDao().insertAllMovies(data)

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.movieRemoteKeysDao().getKeyByMovieId(repo.title)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.movieRemoteKeysDao().getKeyByMovieId(movie.title)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { title ->
                database.movieRemoteKeysDao().getKeyByMovieId(title)
            }
        }
    }
}