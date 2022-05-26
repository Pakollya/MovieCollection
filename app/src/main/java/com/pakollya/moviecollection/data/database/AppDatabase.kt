package com.pakollya.moviecollection.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pakollya.moviecollection.DATABASE_NAME
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
    abstract fun moviesDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
}