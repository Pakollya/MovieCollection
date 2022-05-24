package com.pakollya.moviecollection.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pakollya.moviecollection.data.database.entity.ImageLink
import com.pakollya.moviecollection.data.database.entity.MovieLink

class MovieLinkConverter{
    private val gson = Gson()

    @TypeConverter
    fun stringToMovieLink(data: String?): MovieLink {
        return if (data.isNullOrEmpty()) {
             MovieLink(null, null)
        } else {
            val movieLinkType = object : TypeToken<MovieLink>() {}.type
            gson.fromJson(data, movieLinkType)
        }
    }

    @TypeConverter
    fun movieLinkToString(movieLink: MovieLink?): String {
        return if (movieLink == null) "" else gson.toJson(movieLink)
    }
}

class ImageLinkConverter{
    private val gson = Gson()

    @TypeConverter
    fun stringToMovieLink(data: String?): ImageLink {
        return if (data.isNullOrEmpty()) {
            ImageLink(null)
        } else {
            val imageLinkType = object : TypeToken<ImageLink>() {}.type
            gson.fromJson(data, imageLinkType)
        }
    }

    @TypeConverter
    fun movieLinkToString(imageLink: ImageLink?): String {
        return if (imageLink == null) "" else gson.toJson(imageLink)
    }
}