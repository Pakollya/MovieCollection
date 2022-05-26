package com.pakollya.moviecollection.di.component

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.di.module.InteractorModule
import com.pakollya.moviecollection.domain.MovieInteractor
import dagger.Component

@Component(modules = [InteractorModule::class], dependencies = [RepositoryComponent::class])
@ExperimentalPagingApi
interface InteractorComponent {
    val movieInteractor: MovieInteractor
}