package com.pakollya.moviecollection.di.component

import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.di.module.PresenterModule
import com.pakollya.moviecollection.presentation.main.MainActivity
import com.pakollya.moviecollection.presentation.main.MainContract
import dagger.Component

@Component(modules = [PresenterModule::class], dependencies = [InteractorComponent::class])
@ExperimentalPagingApi
interface PresenterComponent {
    val presenter: MainContract.Presenter

    fun inject(activity: MainActivity)
}