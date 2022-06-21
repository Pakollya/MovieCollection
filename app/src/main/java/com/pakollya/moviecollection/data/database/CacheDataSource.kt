package com.pakollya.moviecollection.data.database

import android.content.Context
import androidx.room.Room

interface CacheDataSource: MovieDataSource, MovieKeyDataSource {

    class BaseDataSource(context: Context, databaseName: String) : CacheDataSource {

        private val appDatabase: AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            databaseName
        ).build()

        override fun movieDao(): MovieDao {
            return appDatabase.movieDao()
        }

        override fun movieRemoteKeyDao(): MovieRemoteKeyDao {
            return appDatabase.movieRemoteKeyDao()
        }
    }
}

interface MovieDataSource{
    fun movieDao(): MovieDao
}

interface MovieKeyDataSource{
    fun movieRemoteKeyDao(): MovieRemoteKeyDao
}