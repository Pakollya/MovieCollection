package com.pakollya.moviecollection.di.module

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.data.database.AppDatabase
import com.pakollya.moviecollection.data.repository.MovieRemoteMediator
import dagger.Module
import dagger.Provides

@Module
@ExperimentalPagingApi
class RemoteMediatorModule {
    @Provides
    fun provideMovieRemoteMediator(movieApiService: MovieApiService, appDatabase: AppDatabase) = MovieRemoteMediator(movieApiService, appDatabase)
}