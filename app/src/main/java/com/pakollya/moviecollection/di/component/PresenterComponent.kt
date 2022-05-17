package com.pakollya.moviecollection.di.component

import com.pakollya.moviecollection.di.PresenterModule
import com.pakollya.moviecollection.presentation.main.MainActivity
import com.pakollya.moviecollection.presentation.main.MainContract
import com.pakollya.moviecollection.presentation.main.MainPresenter
import dagger.Component

@Component(modules = [PresenterModule::class], dependencies = [InteractorComponent::class])
interface PresenterComponent {
    val presenter: MainContract.Presenter

    fun inject(activity: MainActivity)
}