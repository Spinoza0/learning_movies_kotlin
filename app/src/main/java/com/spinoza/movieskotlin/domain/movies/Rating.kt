package com.spinoza.movieskotlin.domain.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    @SerializedName("kp")
    val kinopoiskRating: Double,
) : Parcelable
