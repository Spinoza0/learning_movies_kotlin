package com.spinoza.movieskotlin.domain.movies

import com.google.gson.annotations.SerializedName

class Rating(
    @SerializedName("kp")
    val kinopoiskRating: Double,
) : java.io.Serializable