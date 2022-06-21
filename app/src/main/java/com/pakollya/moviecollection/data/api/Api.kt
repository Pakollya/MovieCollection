package com.pakollya.moviecollection.data.api

import com.pakollya.moviecollection.utils.REQUEST_REVIEWS_ALL
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(REQUEST_REVIEWS_ALL)
    fun getListMovie(
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int
    ): Single<Response<MovieApiResponse>>
}