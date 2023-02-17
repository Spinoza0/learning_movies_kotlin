package com.spinoza.movieskotlin.domain.model

data class MovieDetails(
    val movie: Movie,
    val isFavourite: Boolean,
    val links: List<Link>,
    val reviews: List<Review>,
)