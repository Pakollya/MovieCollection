package com.pakollya.moviecollection

import android.app.Application
import com.pakollya.moviecollection.di.AppComponent
import com.pakollya.moviecollection.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }
}