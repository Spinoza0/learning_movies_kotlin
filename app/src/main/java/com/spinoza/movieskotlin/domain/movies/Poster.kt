package com.spinoza.movieskotlin.domain.movies

import com.google.gson.annotations.SerializedName

class Poster(
    @SerializedName("url")
    val url: String,
) : java.io.Serializable