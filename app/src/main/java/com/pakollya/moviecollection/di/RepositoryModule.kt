package com.pakollya.moviecollection.di

import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.data.repository.MovieListRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideMovieListRepository(movieApiService: MovieApiService) = MovieListRepository(movieApiService)
}