package com.spinoza.movieskotlin.domain.model

data class Review(
    val title: String,
    val type: String,
    val review: String,
    val author: String,
)