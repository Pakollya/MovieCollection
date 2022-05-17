package com.pakollya.moviecollection

import android.app.Application
import com.pakollya.moviecollection.di.ApiModule
import com.pakollya.moviecollection.di.InteractorModule
import com.pakollya.moviecollection.di.PresenterModule
import com.pakollya.moviecollection.di.RepositoryModule
import com.pakollya.moviecollection.di.component.*

class App: Application() {

    lateinit var presenterComponent: PresenterComponent

    override fun onCreate() {
        super.onCreate()
        val apiComponent = DaggerApiComponent.builder().apiModule(ApiModule()).build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .apiComponent(apiComponent)
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