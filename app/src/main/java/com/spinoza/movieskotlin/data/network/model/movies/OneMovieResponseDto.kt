package com.spinoza.movieskotlin.data.network.model.movies

import com.google.gson.annotations.SerializedName
import com.spinoza.movieskotlin.data.network.model.links.LinkItemsListDto

class OneMovieResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("year")
    val year: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("poster")
    val poster: PosterDto,

    @SerializedName("rating")
    val rating: RatingDto,

    @SerializedName("watchability")
    val linkItemsList: LinkItemsListDto,
)