package com.spinoza.movieskotlin.domain.repository

import androidx.lifecycle.LiveData
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.MoviesState

interface MoviesRepository {
    fun getState(): LiveData<MoviesState>
    suspend fun loadMovies()
    suspend fun loadOneMovie(movie: Movie)
    suspend fun getAllFavouriteMovies()
    suspend fun changeFavouriteStatus(movie: Movie)
}