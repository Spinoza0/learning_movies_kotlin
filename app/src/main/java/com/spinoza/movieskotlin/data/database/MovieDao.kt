package com.spinoza.movieskotlin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spinoza.movieskotlin.data.database.model.MovieDbModel
import com.spinoza.movieskotlin.data.database.model.MovieDbModel.Companion.TABLE_FAVOURITES

@Dao
interface MovieDao {
    @Query("SELECT * FROM $TABLE_FAVOURITES")
    fun getAllFavouriteMovies(): List<MovieDbModel>

    @Query("SELECT * FROM $TABLE_FAVOURITES WHERE id=:movieId")
    fun getFavouriteMovie(movieId: Int): MovieDbModel

    @Insert
    fun insertMovie(movie: MovieDbModel)

    @Query("DELETE FROM $TABLE_FAVOURITES WHERE id=:movieId")
    fun removeMovie(movieId: Int)

    @Query("SELECT EXISTS (SELECT * FROM $TABLE_FAVOURITES WHERE movieId=:movieId)")
    suspend fun isMovieFavourite(movieId: Int): Boolean
}