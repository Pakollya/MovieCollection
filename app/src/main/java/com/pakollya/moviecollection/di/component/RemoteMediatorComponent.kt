package com.pakollya.moviecollection.di.component

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.data.repository.MovieRemoteMediator
import com.pakollya.moviecollection.di.module.RemoteMediatorModule
import dagger.Component

@ExperimentalPagingApi
@Component(modules = [RemoteMediatorModule::class], dependencies = [ApiComponent::class, DatabaseComponent::class])
interface RemoteMediatorComponent {
    val remoteMediator: MovieRemoteMediator
}