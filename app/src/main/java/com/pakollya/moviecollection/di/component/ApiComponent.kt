package com.pakollya.moviecollection.di.component

import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.di.ApiModule
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    val movieApiService: MovieApiService
}