package com.pakollya.moviecollection.di.module

import com.pakollya.moviecollection.data.repository.MovieListRepository
import com.pakollya.moviecollection.data.repository.MoviePagingSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideMovieListRepository(moviePagingSource: MoviePagingSource) = MovieListRepository(moviePagingSource)
}