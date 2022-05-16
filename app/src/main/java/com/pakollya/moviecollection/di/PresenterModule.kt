package com.pakollya.moviecollection.di

import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideMainPresenter(movieInteractor: MovieInteractor)= MainPresenter(movieInteractor)
}