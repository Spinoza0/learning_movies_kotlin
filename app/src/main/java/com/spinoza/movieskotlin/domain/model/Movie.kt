package com.spinoza.movieskotlin.domain.model

data class Movie(
    val id: Int,
    val year: Int,
    val name: String,
    val description: String,
    val poster: String,
    val rating: Double,
    var isFavourite: Boolean,
)