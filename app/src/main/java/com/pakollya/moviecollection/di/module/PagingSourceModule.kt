package com.pakollya.moviecollection.di.module

import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.data.repository.MoviePagingSource
import dagger.Module
import dagger.Provides

@Module
class PagingSourceModule {
    @Provides
    fun provideMoviePagingSource(movieApiService: MovieApiService) = MoviePagingSource(movieApiService)
}