package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.ScreenType
import com.spinoza.movieskotlin.domain.usecase.GetAllFavouritesMoviesUseCase
import com.spinoza.movieskotlin.domain.usecase.GetStateUseCase
import com.spinoza.movieskotlin.domain.usecase.LoadMovieDetailsUseCase
import com.spinoza.movieskotlin.domain.usecase.ResetStateUseCase
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel(
    getStateUseCase: GetStateUseCase,
    private val resetStateUseCase: ResetStateUseCase,
    private val getAllFavouritesMoviesUseCase: GetAllFavouritesMoviesUseCase,
    private val loadMovieDetailsUseCase: LoadMovieDetailsUseCase,
) : ViewModel() {

    val state = getStateUseCase(SCREEN_TYPE)

    init {
        loadAllMovies()
    }

    fun loadAllMovies() {
        viewModelScope.launch {
            getAllFavouritesMoviesUseCase()
        }
    }

    fun loadMovieDetails(movie: Movie) {
        viewModelScope.launch {
            loadMovieDetailsUseCase(movie, SCREEN_TYPE)
        }
    }

    fun resetState() {
        viewModelScope.launch {
            resetStateUseCase(SCREEN_TYPE)
        }
    }

    companion object {
        private val SCREEN_TYPE = ScreenType.FAVOURITE_MOVIES
    }
}