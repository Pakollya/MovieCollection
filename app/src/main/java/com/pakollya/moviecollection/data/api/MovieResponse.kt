package com.pakollya.moviecollection.data.api

import com.pakollya.moviecollection.data.database.entity.Movie

data class MovieResponse (val results: List<Movie>)