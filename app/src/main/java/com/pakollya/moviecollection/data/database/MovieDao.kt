package com.pakollya.moviecollection.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.data.database.entity.MovieRemoteKey
import io.reactivex.Single

@Dao
interface MovieDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListMovie(movies: List<Movie>)

    @Query("SELECT * FROM movie ORDER BY id ASC")
    fun listPagingMovie(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie ORDER BY id ASC")
    fun listMovie(): Single<List<Movie>>

    @Query("DELETE FROM movie")
    fun clearListMovie()

    @Query("SELECT * FROM movie WHERE title = :title")
    fun movieByTitle(title: String): Single<Movie>
}

@Dao
interface MovieRemoteKeyDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListKeys(remoteKeys: List<MovieRemoteKey>)

    @Query("SELECT * FROM movie_remote_key WHERE movieId = :movieId")
    fun keyByMovieId(movieId: Long): MovieRemoteKey?

    @Query("DELETE FROM movie_remote_key")
    fun clearListKey()
}