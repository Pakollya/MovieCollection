package com.pakollya.moviecollection.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
//        TODO: Добавить placeholder и error
        Glide.with(view.context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(view)
    }
}