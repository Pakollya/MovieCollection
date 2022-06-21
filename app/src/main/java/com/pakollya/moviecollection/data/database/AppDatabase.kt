package com.pakollya.moviecollection.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.database.entity.MovieRemoteKey

@Database(
    entities = [
        Movie::class,
        MovieRemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [MovieLinkConverter::class, ImageLinkConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao
}