package com.spinoza.movieskotlin.data.network.model.movies

import com.google.gson.annotations.SerializedName

class MoviesResponseDto(
    @SerializedName("docs")
    val movies: List<MovieDto>,
)