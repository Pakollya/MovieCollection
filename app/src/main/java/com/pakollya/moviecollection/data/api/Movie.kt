package com.pakollya.moviecollection.data.api

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("display_title")
    val title: String,

    @SerializedName("summary_short")
    val summary: String,

    @SerializedName("link")
    val movieLink: MovieLink,

    @SerializedName("multimedia")
    val imageLink: ImageLink
) {
    fun getImageUrl() = imageLink.src

    fun getMovieUrl() = movieLink.url
}

data class MovieLink(val type: String, val url: String, val suggested_link_text: String)

data class ImageLink(val type: String, val src: String, val height: Int, val width: Int)