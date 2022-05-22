package com.pakollya.moviecollection.di.component

import com.pakollya.moviecollection.data.repository.MovieListRepository
import com.pakollya.moviecollection.di.module.RepositoryModule
import dagger.Component

@Component(modules = [RepositoryModule::class], dependencies = [PagingSourceComponent::class])
interface RepositoryComponent{
    val movieListRepository: MovieListRepository
}