package com.pakollya.moviecollection.data.api

import com.pakollya.moviecollection.RETRY_ATTEMPTS
import com.pakollya.moviecollection.TIMEOUT
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MovieApiService(val api: Api) {

    fun getAllMovies(apiKey: String, offset: Int): Single<Response<MovieResponse>> = api.getAllMovies(apiKey, offset)
        .compose(
            SingleTransformer { upstream ->  upstream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .retry(RETRY_ATTEMPTS)
            }
        )

}