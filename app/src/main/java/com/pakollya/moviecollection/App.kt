package com.pakollya.moviecollection

import android.app.Application
import com.pakollya.moviecollection.di.component.*
import com.pakollya.moviecollection.di.module.*

class App: Application() {

    lateinit var presenterComponent: PresenterComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    fun initDagger() {
        val apiComponent = DaggerApiComponent.builder().apiModule(ApiModule()).build()

        val pagingSourceComponent = DaggerPagingSourceComponent.builder()
            .apiComponent(apiComponent)
            .pagingSourceModule(PagingSourceModule())
            .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .pagingSourceComponent(pagingSourceComponent)
            .repositoryModule(RepositoryModule())
            .build()

        val interactorComponent = DaggerInteractorComponent.builder()
            .repositoryComponent(repositoryComponent)
            .interactorModule(InteractorModule())
            .build()

        presenterComponent = DaggerPresenterComponent.builder()
            .interactorComponent(interactorComponent)
            .presenterModule(PresenterModule())
            .build()
    }
}