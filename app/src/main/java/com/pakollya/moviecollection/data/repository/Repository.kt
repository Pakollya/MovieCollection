package com.pakollya.moviecollection.data.repository

import androidx.paging.PagingData
import com.pakollya.moviecollection.data.database.entity.Movie
import io.reactivex.Flowable
import io.reactivex.Single

interface Repository {

    fun listMovie(): Flowable<PagingData<Movie>>
    fun movieByTitle(title: String): Single<Movie>
}