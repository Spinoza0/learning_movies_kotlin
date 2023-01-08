package com.spinoza.movieskotlin.domain.links

import com.google.gson.annotations.SerializedName

class LinkResponse(
    @SerializedName("watchability")
    val linkItemsList: LinkItemsList,
)