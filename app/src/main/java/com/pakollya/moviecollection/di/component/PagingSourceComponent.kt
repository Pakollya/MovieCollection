package com.pakollya.moviecollection.di.component

import com.pakollya.moviecollection.data.repository.MoviePagingSource
import com.pakollya.moviecollection.di.module.PagingSourceModule
import dagger.Component

@Component(modules = [PagingSourceModule::class], dependencies = [ApiComponent::class])
interface PagingSourceComponent {
    val moviePagingSource: MoviePagingSource
}