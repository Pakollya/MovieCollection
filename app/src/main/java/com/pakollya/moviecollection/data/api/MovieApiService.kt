package com.pakollya.moviecollection.data.api

import com.pakollya.moviecollection.RETRY_ATTEMPTS
import com.pakollya.moviecollection.TIMEOUT
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieApiService @Inject constructor (val api: Api) {

    fun getAllMovies(apiKey: String, offset: Int): Single<Response<MovieResponse>> = api.getAllMovies(apiKey, offset)
        .compose { upstream ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .retry(RETRY_ATTEMPTS)
        }
}