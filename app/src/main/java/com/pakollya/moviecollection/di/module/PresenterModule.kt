package com.pakollya.moviecollection.di.module

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.detail.DetailContract
import com.pakollya.moviecollection.presentation.detail.DetailPresenter
import com.pakollya.moviecollection.presentation.main.MainContract
import com.pakollya.moviecollection.presentation.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
@ExperimentalPagingApi
class MainPresenterModule {
    @Provides
    fun provideMainPresenter(movieInteractor: MovieInteractor): MainContract.Presenter = MainPresenter(movieInteractor)
}

@Module
@ExperimentalPagingApi
class DetailPresenterModule {

    @Provides
    fun provideDetailPresenter(movieInteractor: MovieInteractor): DetailContract.Presenter = DetailPresenter(movieInteractor)
}