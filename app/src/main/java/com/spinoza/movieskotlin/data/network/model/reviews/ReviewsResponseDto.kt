package com.spinoza.movieskotlin.data.network.model.reviews

import com.google.gson.annotations.SerializedName

class ReviewsResponseDto(
    @SerializedName("docs")
    val reviews: List<ReviewDto>,
)