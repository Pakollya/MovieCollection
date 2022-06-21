package com.pakollya.moviecollection.data.api

import com.pakollya.moviecollection.utils.RETRY_ATTEMPTS
import com.pakollya.moviecollection.utils.TIMEOUT
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MovieApiService(
private val api: Api,
private val apiKey: String
) {

    fun movieApiResponse(offset: Int): Single<Response<MovieApiResponse>> = api.getListMovie(apiKey, offset)
        .compose { upstream ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .retry(RETRY_ATTEMPTS)
        }
}