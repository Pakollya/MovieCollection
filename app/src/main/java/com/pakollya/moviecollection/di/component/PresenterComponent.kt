package com.pakollya.moviecollection.di.component

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.di.module.DetailPresenterModule
import com.pakollya.moviecollection.di.module.MainPresenterModule
import com.pakollya.moviecollection.presentation.detail.DetailActivity
import com.pakollya.moviecollection.presentation.detail.DetailContract
import com.pakollya.moviecollection.presentation.main.MainActivity
import com.pakollya.moviecollection.presentation.main.MainContract
import dagger.Component

@Component(modules = [MainPresenterModule::class], dependencies = [InteractorComponent::class])
@ExperimentalPagingApi
interface MainPresenterComponent {
    val mainPresenter: MainContract.Presenter

    fun inject(activity: MainActivity)
}

@Component(modules = [DetailPresenterModule::class], dependencies = [InteractorComponent::class])
@ExperimentalPagingApi
interface DetailPresenterComponent {
    val detailPresenter: DetailContract.Presenter

    fun inject(activity: DetailActivity)
}