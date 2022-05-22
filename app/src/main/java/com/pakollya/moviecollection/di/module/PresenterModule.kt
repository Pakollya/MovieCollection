package com.pakollya.moviecollection.di.module

import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.main.MainContract
import com.pakollya.moviecollection.presentation.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideMainPresenter(movieInteractor: MovieInteractor): MainContract.Presenter = MainPresenter(movieInteractor)
}