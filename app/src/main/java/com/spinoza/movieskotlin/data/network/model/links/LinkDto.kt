package com.spinoza.movieskotlin.data.network.model.links

import com.google.gson.annotations.SerializedName

data class LinkDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)