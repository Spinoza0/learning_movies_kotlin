package com.spinoza.movieskotlin.domain.repository

import androidx.lifecycle.LiveData
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.MoviesState
import com.spinoza.movieskotlin.domain.model.ScreenType

interface MoviesRepository {
    fun getState(screenType: ScreenType): LiveData<MoviesState>
    fun resetState(screenType: ScreenType)
    suspend fun loadAllMovies()
    suspend fun getMoviesFromCache()
    suspend fun loadMovieDetails(movie: Movie, screenType: ScreenType)
    suspend fun getAllFavouriteMovies()
    suspend fun changeFavouriteStatus(movie: Movie)
}