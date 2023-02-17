package com.spinoza.movieskotlin.data.network.model.links

import com.google.gson.annotations.SerializedName

class LinkItemsListDto(
    @SerializedName("items")
    val items: List<LinkDto>,
)