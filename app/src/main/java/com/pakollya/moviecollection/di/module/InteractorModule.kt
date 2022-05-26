package com.pakollya.moviecollection.di.module

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.data.repository.MovieListRepository
import com.pakollya.moviecollection.domain.MovieInteractor
import dagger.Module
import dagger.Provides

@Module
@ExperimentalPagingApi
class InteractorModule {
    @Provides
    fun provideMovieInteractor(repository: MovieListRepository) = MovieInteractor(repository)
}