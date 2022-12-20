package com.spinoza.movieskotlin.links

import com.google.gson.annotations.SerializedName

class Link(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)