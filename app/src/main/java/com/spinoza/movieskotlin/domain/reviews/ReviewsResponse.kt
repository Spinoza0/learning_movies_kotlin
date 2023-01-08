package com.spinoza.movieskotlin.domain.reviews

import com.google.gson.annotations.SerializedName

class ReviewsResponse(
    @SerializedName("docs")
    val reviews: List<Review>,
)