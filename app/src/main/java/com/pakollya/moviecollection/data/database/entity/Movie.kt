package com.pakollya.moviecollection.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.pakollya.moviecollection.data.database.ImageLinkConverter
import com.pakollya.moviecollection.data.database.MovieLinkConverter

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("display_title")
    val title: String,

    @SerializedName("byline")
    val byline: String,

    @SerializedName("headline")
    val headline: String,

    @SerializedName("summary_short")
    val summary: String,

    @SerializedName("publication_date")
    val date: String,

    @SerializedName("link")
    @TypeConverters(MovieLinkConverter::class)
    val movieLink: MovieLink,

    @SerializedName("multimedia")
    @TypeConverters(ImageLinkConverter::class)
    val imageLink: ImageLink
) {
    fun getImageUrl() = imageLink.src

    fun getMovieUrl() = movieLink.url

    fun getLinkText() = movieLink.link_text
}

data class MovieLink(val url: String?, @SerializedName("suggested_link_text") val link_text: String?)

data class ImageLink(val src: String?)

@Entity(tableName = "movie_remote_key")
data class MovieRemoteKey(
    @PrimaryKey
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)