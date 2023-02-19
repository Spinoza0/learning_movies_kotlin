package com.spinoza.movieskotlin.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val year: Int,
    val name: String,
    val description: String,
    val poster: String,
    val rating: Double,
    val ratingText: String,
) : Parcelable