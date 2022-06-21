package com.pakollya.moviecollection.domain

import androidx.paging.PagingData
import com.pakollya.moviecollection.data.database.entity.Movie
import io.reactivex.Flowable
import io.reactivex.Single

interface Interactor {

    fun listMovie(): Flowable<PagingData<Movie>>

    fun movieByTitle(title: String): Single<Movie>
}