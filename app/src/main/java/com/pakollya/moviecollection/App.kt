package com.pakollya.moviecollection

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.di.AppComponent
import com.pakollya.moviecollection.di.DaggerAppComponent


@ExperimentalPagingApi
class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    fun initDagger() {
        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }
}