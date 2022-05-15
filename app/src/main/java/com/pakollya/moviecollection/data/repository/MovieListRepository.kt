package com.pakollya.moviecollection.data.repository

import com.pakollya.moviecollection.data.api.Movie
import com.pakollya.moviecollection.data.api.MovieApiService
import io.reactivex.Single

class MovieListRepository(private val apiService: MovieApiService) {
    fun getMovies(apiKey: String, offset: Int): Single<List<Movie>> {
        return apiService.getAllMovies(apiKey, offset)
            .flatMap{ movieResponse ->
                return@flatMap if (movieResponse.isSuccessful) {
                        Single.just(movieResponse.body()?.results)
                    } else {
                        Single.error(Exception("Network error code: ${movieResponse.code()}"))
                    }
        }
    }
}