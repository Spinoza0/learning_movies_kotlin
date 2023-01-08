package com.spinoza.movieskotlin.domain.links

import com.google.gson.annotations.SerializedName

class LinkItemsList(
    @SerializedName("items")
    val items: List<Link>,
)