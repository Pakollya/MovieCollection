package com.pakollya.moviecollection.di.component

import com.pakollya.moviecollection.di.module.InteractorModule
import com.pakollya.moviecollection.domain.MovieInteractor
import dagger.Component

@Component(modules = [InteractorModule::class], dependencies = [RepositoryComponent::class])
interface InteractorComponent {
    val movieInteractor: MovieInteractor
}