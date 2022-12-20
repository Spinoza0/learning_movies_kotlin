package com.spinoza.movieskotlin.movies

import com.google.gson.annotations.SerializedName

class MoviesResponse(
    @SerializedName("docs")
    val movies: List<Movie>,
)