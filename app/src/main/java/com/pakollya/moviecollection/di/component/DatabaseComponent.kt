package com.pakollya.moviecollection.di.component

import com.pakollya.moviecollection.data.database.AppDatabase
import com.pakollya.moviecollection.di.module.DatabaseModule
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val appDatabase: AppDatabase
}