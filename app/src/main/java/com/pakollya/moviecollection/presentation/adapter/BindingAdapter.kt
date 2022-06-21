package com.pakollya.moviecollection.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pakollya.moviecollection.R
import jp.wasabeef.glide.transformations.BlurTransformation

@BindingAdapter("imageFromUrl")
fun imageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.movie_placeholder)
            .error(R.drawable.movie_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(view)
    }
}

@BindingAdapter("imageFromUrlWithBlur")
fun imageFromUrlWithBlur(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.movie_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(10)))
            .into(view)
    }
}