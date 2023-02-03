package com.spinoza.movieskotlin.domain.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Poster(
    @SerializedName("url")
    val url: String,
) : Parcelable