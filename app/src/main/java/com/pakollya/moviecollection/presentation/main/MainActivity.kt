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
import com.pakollya.moviecollection.presentation.adapter.animation.AddableAnimator
import com.pakollya.moviecollection.presentation.adapter.animation.SimpleAnimator
import com.pakollya.moviecollection.presentation.adapter.animation.SlideInLeftAnimator
import com.pakollya.moviecollection.presentation.adapter.decoration.VerticalItemDecoration
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
        val movieAdapter = MovieAdapter()
        movieAdapter.setItemClickListener(itemClickListener)
        with(binding.movieList) {
            adapter = movieAdapter

//            addItemDecoration(HorizontalItemDecoration(20))
            addItemDecoration(VerticalItemDecoration(30, 30))

            itemAnimator = AddableAnimator(SimpleAnimator()).also { animator ->
                animator.addViewTypeAnimation(R.layout.movie_item, SlideInLeftAnimator())
                animator.addDuration = 500L
                animator.removeDuration = 500L
            }
        }

        binding.movieList.postDelayed({
                movieAdapter.submitData(this.lifecycle, movies)
            }, 300L)
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