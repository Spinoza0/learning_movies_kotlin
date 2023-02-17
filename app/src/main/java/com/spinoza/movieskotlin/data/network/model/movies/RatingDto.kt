package com.spinoza.movieskotlin.data.network.model.movies

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("kp")
    val kinopoiskRating: Double,
)