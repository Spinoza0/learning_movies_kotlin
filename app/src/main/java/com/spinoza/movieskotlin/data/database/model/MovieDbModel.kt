package com.spinoza.movieskotlin.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spinoza.movieskotlin.data.database.model.MovieDbModel.Companion.TABLE_FAVOURITES

@Entity(tableName = TABLE_FAVOURITES)
data class MovieDbModel(
    @PrimaryKey
    val id: Int,
    val year: Int,
    val name: String,
    val description: String,
    val poster: String,
    val rating: Double,
) {
    companion object {
        const val TABLE_FAVOURITES = "favourite_movies"
    }
}