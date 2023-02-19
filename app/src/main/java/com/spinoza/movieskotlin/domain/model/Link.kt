package com.spinoza.movieskotlin.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val name: String,
    val url: String,
): Parcelable