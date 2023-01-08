package com.spinoza.movieskotlin.domain.movies

import com.google.gson.annotations.SerializedName

class MoviesResponse(
    @SerializedName("docs")
    val movies: List<Movie>,
)