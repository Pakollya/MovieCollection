package com.pakollya.moviecollection

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    fun initDagger() {
        //TODO Добавить дагер
    }
}