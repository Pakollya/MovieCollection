package com.pakollya.moviecollection.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.ActivityDetailBinding
import com.pakollya.moviecollection.presentation.main.appComponent
import com.pakollya.moviecollection.utils.TITLE_KEY
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View {

    @Inject
    lateinit var detailPresenter: DetailContract.Presenter
    private lateinit var binding: ActivityDetailBinding
    private var movieUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieTitle = intent.extras?.getString(TITLE_KEY)
        if (movieTitle.isNullOrEmpty()) {
            onBackPressed()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        appComponent.inject(this)
        detailPresenter.attachWithView(this)
        initializeToolbar(true)
        binding.linkButton.setOnClickListener {
            movieUrl?.let { linkIntent(it) }
        }

        movieTitle?.let { detailPresenter.movieByTitle(it) }
    }

    override fun showMovie(movie: Movie) {
        binding.movie = movie
        movieUrl = movie.movieUrl()
    }

    override fun context(): Context = this

    private fun initializeToolbar(clickEnabled: Boolean) {
        val toolbar = binding.toolbar
        setActionBar(toolbar)
        if (clickEnabled) {
            toolbar.setNavigationOnClickListener{ onBackPressed() }
        }
        val actionBar = actionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }

    private fun linkIntent(url: String) {
        val linkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        linkIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(linkIntent)
    }
}