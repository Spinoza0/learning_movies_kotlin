package com.spinoza.movieskotlin.links

import com.google.gson.annotations.SerializedName

class LinkResponse(
    @SerializedName("watchability")
    val linkItemsList: LinkItemsList,
)