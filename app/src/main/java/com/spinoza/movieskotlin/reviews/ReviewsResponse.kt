package com.spinoza.movieskotlin.reviews

import com.google.gson.annotations.SerializedName

class ReviewsResponse(
    @SerializedName("docs")
    val reviews: List<Review>,
)