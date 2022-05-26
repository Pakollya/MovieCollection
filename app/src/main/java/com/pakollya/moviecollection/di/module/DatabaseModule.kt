package com.pakollya.moviecollection.di.module

import android.app.Application
import android.content.Context
import com.pakollya.moviecollection.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val app: Application) {
    @Provides
    fun provideAppDatabase() = AppDatabase.getInstance(app.applicationContext)
}