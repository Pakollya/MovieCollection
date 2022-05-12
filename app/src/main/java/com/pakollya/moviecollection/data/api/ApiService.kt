package com.pakollya.moviecollection.data.api

import com.pakollya.moviecollection.REQUEST_REVIEWS_ALL
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(REQUEST_REVIEWS_ALL)
    fun getAllMovies(
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int
    ): Single<Response<List<Movie>>>
}