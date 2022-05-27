package com.pakollya.moviecollection.presentation.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.App
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.data.database.entity.Movie
import com.pakollya.moviecollection.databinding.ActivityDetailBinding
import com.pakollya.moviecollection.di.component.DetailPresenterComponent
import com.pakollya.moviecollection.presentation.base.BaseActivity
import javax.inject.Inject

@ExperimentalPagingApi
class DetailActivity : BaseActivity(), DetailContract.View {

    @Inject
    lateinit var detailPresenter: DetailContract.Presenter
    private lateinit var binding: ActivityDetailBinding
    private var movieTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailPresenterComponent.inject(this)
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
}

@ExperimentalPagingApi
val Context.detailPresenterComponent: DetailPresenterComponent
    get() = when(this) {
        is App -> detailPresenterComponent
        else -> this.applicationContext.detailPresenterComponent
    }