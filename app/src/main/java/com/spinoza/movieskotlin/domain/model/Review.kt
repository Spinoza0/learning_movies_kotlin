package com.spinoza.movieskotlin.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val title: String,
    val type: String,
    val review: String,
    val author: String,
): Parcelable