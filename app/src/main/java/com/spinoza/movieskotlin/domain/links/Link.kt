package com.spinoza.movieskotlin.domain.links

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)