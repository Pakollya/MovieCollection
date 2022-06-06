package com.pakollya.moviecollection.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import com.pakollya.moviecollection.App
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.ActivityMainBinding
import com.pakollya.moviecollection.di.AppComponent
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
    private lateinit var movieAdapter: MovieAdapter

    private val itemClickListener: MovieItemClickListener<Movie> = object : MovieItemClickListener<Movie> {
        override fun openDetail(movie: Movie) {
            openItemDetail(movie.title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setActionBar(binding.toolbar)
        appComponent.inject(this)
        mainPresenter.attachWithView(this)

        setAdapter()
        setAnimation()

        mainPresenter.getMovies()
    }

    override fun showMovies(movies: PagingData<Movie>) {
        binding.movieList.postDelayed({
                movieAdapter.submitData(this.lifecycle, movies)
            }, 500L)

    }

    override fun getContext(): Context = this

    private fun openItemDetail(title: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("MovieTitle", title)
        startActivity(intent)
    }

    private fun setAdapter() {
        movieAdapter = MovieAdapter()
        movieAdapter.setItemClickListener(itemClickListener)

        movieAdapter.addLoadStateListener { state ->
//            binding.movieList.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading
            val refreshState = state.refresh
            if (refreshState is LoadState.Error) {
                Snackbar.make(
                    binding.root,
                    refreshState.error.localizedMessage ?: "",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }

        binding.movieList.adapter = movieAdapter
//                        .withLoadStateFooter(LoaderStateAdapter())
    }

    private fun setAnimation() {
        with(binding.movieList) {
//            addItemDecoration(HorizontalItemDecoration(20))
            addItemDecoration(VerticalItemDecoration(30, 30))

            itemAnimator = AddableAnimator(SimpleAnimator()).also { animator ->
                animator.addViewTypeAnimation(R.layout.movie_item, SlideInLeftAnimator())
                animator.addDuration = 1000L
                animator.removeDuration = 500L
            }
        }
    }
}

@ExperimentalPagingApi
val Context.appComponent: AppComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }