package com.pakollya.moviecollection.di.module

import com.pakollya.moviecollection.data.repository.MovieListRepository
import com.pakollya.moviecollection.domain.MovieInteractor
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {
    @Provides
    fun provideMovieInteractor(repository: MovieListRepository) = MovieInteractor(repository)
}