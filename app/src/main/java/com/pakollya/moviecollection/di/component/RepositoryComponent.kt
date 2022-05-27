package com.pakollya.moviecollection.di.component

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.data.repository.MovieListRepository
import com.pakollya.moviecollection.di.module.RepositoryModule
import dagger.Component

@ExperimentalPagingApi
@Component(modules = [RepositoryModule::class], dependencies = [DatabaseComponent::class, RemoteMediatorComponent::class])
interface RepositoryComponent{
    val movieListRepository: MovieListRepository
}