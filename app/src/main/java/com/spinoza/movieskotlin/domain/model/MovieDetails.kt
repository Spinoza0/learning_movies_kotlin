package com.spinoza.movieskotlin.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    val movie: Movie,
    val isFavourite: Boolean,
    val links: List<Link>,
    val reviews: List<Review>,
): Parcelable