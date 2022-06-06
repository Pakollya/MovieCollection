package com.pakollya.moviecollection.presentation.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.ActivityDetailBinding
import com.pakollya.moviecollection.presentation.main.appComponent
import javax.inject.Inject

@ExperimentalPagingApi
class DetailActivity : AppCompatActivity(), DetailContract.View {

    @Inject
    lateinit var detailPresenter: DetailContract.Presenter
    private lateinit var binding: ActivityDetailBinding
    private var movieTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        appComponent.inject(this)
        detailPresenter.attachWithView(this)
//        TODO: Добавить обработку null
        movieTitle = intent.extras?.getString("MovieTitle")
        Log.e("Title", movieTitle.toString())
        movieTitle?.let { detailPresenter.getMovie(it) }
    }

    override fun showMovie(movie: Movie) {
        initializeToolbar(movie.title, true)
        binding.movie = movie
    }

    override fun getContext(): Context = this

    private fun initializeToolbar(title: String?, clickEnabled: Boolean) {
        val toolbar = binding.toolbar
        setActionBar(toolbar)
        if (clickEnabled) {
            toolbar.setNavigationOnClickListener{ onBackPressed() }
        }
        val actionBar = actionBar
        if (actionBar != null) {
            title?.let { actionBar.title = it }
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }
}