package com.pakollya.moviecollection.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.database.entity.MovieRemoteKey

@Dao
interface MovieDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)

    @Query("SELECT * FROM movie ORDER BY id ASC")
    fun getAllMovie(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    fun clearAllMovies()

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: Int): Movie?
}

@Dao
interface MovieRemoteKeyDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllKeys(remoteKeys: List<MovieRemoteKey>)

    @Query("SELECT * FROM movie_remote_key WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): MovieRemoteKey?

    @Query("DELETE FROM movie_remote_key")
    fun clearAllKeys()
}