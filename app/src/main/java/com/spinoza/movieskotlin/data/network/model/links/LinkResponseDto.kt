package com.spinoza.movieskotlin.data.network.model.links

import com.google.gson.annotations.SerializedName

class LinkResponseDto(
    @SerializedName("watchability")
    val linkItemsList: LinkItemsListDto,
)