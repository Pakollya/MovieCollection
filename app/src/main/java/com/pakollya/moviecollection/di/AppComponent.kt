@file:OptIn(ExperimentalPagingApi::class)

package com.pakollya.moviecollection.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.data.database.CacheDataSource.BaseDataSource
import com.pakollya.moviecollection.data.repository.MovieListRepository
import com.pakollya.moviecollection.data.repository.MoviePagingSource
import com.pakollya.moviecollection.data.repository.MovieRemoteMediator
import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.detail.DetailActivity
import com.pakollya.moviecollection.presentation.detail.DetailContract
import com.pakollya.moviecollection.presentation.main.MainActivity
import com.pakollya.moviecollection.presentation.main.MainContract
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    val movieApiService: MovieApiService
    val appDatabase: BaseDataSource
    val moviePagingSource: MoviePagingSource
    val remoteMediator: MovieRemoteMediator
    val movieListRepository: MovieListRepository
    val interactor: MovieInteractor
    val mainPresenter: MainContract.Presenter
    val detailPresenter: DetailContract.Presenter

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}