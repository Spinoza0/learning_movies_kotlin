package com.spinoza.movieskotlin.movies

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite_movies")
class Movie(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("year")
    val year: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("poster")
    @Embedded
    val poster: Poster,

    @SerializedName("rating")
    @Embedded
    val rating: Rating,
) : java.io.Serializable