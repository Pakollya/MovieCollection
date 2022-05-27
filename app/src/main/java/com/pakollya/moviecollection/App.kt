package com.pakollya.moviecollection

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.di.component.*
import com.pakollya.moviecollection.di.module.*

@ExperimentalPagingApi
class App: Application() {

    lateinit var mainPresenterComponent: MainPresenterComponent
    lateinit var detailPresenterComponent: DetailPresenterComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    fun initDagger() {
        val apiComponent = DaggerApiComponent.builder().apiModule(ApiModule()).build()

        val databaseComponent = DaggerDatabaseComponent.builder().databaseModule(DatabaseModule(this)).build()

        val remoteMediatorComponent = DaggerRemoteMediatorComponent.builder()
            .apiComponent(apiComponent)
            .databaseComponent(databaseComponent)
            .remoteMediatorModule(RemoteMediatorModule())
            .build()

//        val pagingSourceComponent = DaggerPagingSourceComponent.builder()
//            .apiComponent(apiComponent)
//            .pagingSourceModule(PagingSourceModule())
//            .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .remoteMediatorComponent(remoteMediatorComponent)
            .databaseComponent(databaseComponent)
            .repositoryModule(RepositoryModule())
            .build()

        val interactorComponent = DaggerInteractorComponent.builder()
            .repositoryComponent(repositoryComponent)
            .interactorModule(InteractorModule())
            .build()

        mainPresenterComponent = DaggerMainPresenterComponent.builder()
            .interactorComponent(interactorComponent)
            .mainPresenterModule(MainPresenterModule())
            .build()

        detailPresenterComponent = DaggerDetailPresenterComponent.builder()
            .interactorComponent(interactorComponent)
            .detailPresenterModule(DetailPresenterModule())
            .build()
    }
}