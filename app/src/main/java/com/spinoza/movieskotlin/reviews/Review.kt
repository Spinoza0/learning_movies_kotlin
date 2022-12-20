package com.spinoza.movieskotlin.reviews

import com.google.gson.annotations.SerializedName

class Review(
    @SerializedName("title")
    val title: String?,

    @SerializedName("type")
    val type: String,

    @SerializedName("review")
    val review: String,

    @SerializedName("author")
    val author: String,
)