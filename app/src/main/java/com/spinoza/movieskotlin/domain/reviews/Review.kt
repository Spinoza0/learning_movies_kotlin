package com.spinoza.movieskotlin.domain.reviews

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("title")
    val title: String?,

    @SerializedName("type")
    val type: String,

    @SerializedName("review")
    val review: String,

    @SerializedName("author")
    val author: String,
)