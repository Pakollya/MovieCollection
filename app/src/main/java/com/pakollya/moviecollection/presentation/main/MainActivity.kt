package com.pakollya.moviecollection.presentation.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingData
import com.pakollya.moviecollection.App
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.ActivityMainBinding
import com.pakollya.moviecollection.di.component.PresenterComponent
import com.pakollya.moviecollection.presentation.adapter.MovieAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenterComponent.inject(this)
        presenter.attachWithView(this)
        presenter.getMovies()
    }

    override fun showMovies(movies: PagingData<Movie>) {
        val adapter = MovieAdapter()
        binding.movieList.adapter = adapter
        adapter.submitData(this.lifecycle, movies)
    }

    override fun getContext(): Context = this
}

val Context.presenterComponent: PresenterComponent
    get() = when(this) {
        is App -> presenterComponent
        else -> this.applicationContext.presenterComponent
    }