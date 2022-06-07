package com.pakollya.moviecollection.presentation.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pakollya.moviecollection.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
//        TODO: Добавить placeholder и error
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.movie_placeholder)
            .error(R.drawable.movie_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(view)
    }
}