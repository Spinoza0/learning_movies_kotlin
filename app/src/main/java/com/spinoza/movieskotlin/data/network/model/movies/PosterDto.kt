package com.spinoza.movieskotlin.data.network.model.movies

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("url")
    val url: String,
)