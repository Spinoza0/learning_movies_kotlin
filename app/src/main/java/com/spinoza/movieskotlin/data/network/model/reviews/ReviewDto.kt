package com.spinoza.movieskotlin.data.network.model.reviews

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("title")
    val title: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("review")
    val review: String,

    @SerializedName("author")
    val author: String,
)