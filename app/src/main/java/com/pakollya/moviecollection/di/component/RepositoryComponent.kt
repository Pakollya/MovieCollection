package com.pakollya.moviecollection.di.component

import com.pakollya.moviecollection.data.repository.MovieListRepository
import com.pakollya.moviecollection.di.RepositoryModule
import dagger.Component

@Component(modules = [RepositoryModule::class], dependencies = [ApiComponent::class])
interface RepositoryComponent{
    val movieListRepository: MovieListRepository
}