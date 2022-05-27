package com.pakollya.moviecollection.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.pakollya.moviecollection.App
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.ActivityMainBinding
import com.pakollya.moviecollection.di.component.MainPresenterComponent
import com.pakollya.moviecollection.presentation.adapter.MovieAdapter
import com.pakollya.moviecollection.presentation.adapter.viewholder.MovieItemClickListener
import com.pakollya.moviecollection.presentation.detail.DetailActivity
import javax.inject.Inject

@ExperimentalPagingApi
class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var mainPresenter: MainContract.Presenter
    private lateinit var binding: ActivityMainBinding

    private val itemClickListener: MovieItemClickListener<Movie> = object : MovieItemClickListener<Movie> {
        override fun openDetail(movie: Movie) {
            openItemDetail(movie.title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setActionBar(binding.toolbar)
        mainPresenterComponent.inject(this)
        mainPresenter.attachWithView(this)
        mainPresenter.getMovies()
    }

    override fun showMovies(movies: PagingData<Movie>) {
        val adapter = MovieAdapter()
        adapter.setItemClickListener(itemClickListener)
        binding.movieList.adapter = adapter
        adapter.submitData(this.lifecycle, movies)
    }

    override fun getContext(): Context = this

    private fun openItemDetail(title: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("MovieTitle", title)
        startActivity(intent)
    }
}

@ExperimentalPagingApi
val Context.mainPresenterComponent: MainPresenterComponent
    get() = when(this) {
        is App -> mainPresenterComponent
        else -> this.applicationContext.mainPresenterComponent
    }