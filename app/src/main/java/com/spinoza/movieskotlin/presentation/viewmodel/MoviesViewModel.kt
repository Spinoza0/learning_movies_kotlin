package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.ScreenType
import com.spinoza.movieskotlin.domain.usecase.GetMoviesFromCacheUseCase
import com.spinoza.movieskotlin.domain.usecase.GetStateUseCase
import com.spinoza.movieskotlin.domain.usecase.LoadAllMoviesUseCase
import com.spinoza.movieskotlin.domain.usecase.LoadMovieDetailsUseCase
import kotlinx.coroutines.launch

class MoviesViewModel(
    getStateUseCase: GetStateUseCase,
    private val loadAllMoviesUseCase: LoadAllMoviesUseCase,
    private val loadMovieDetailsUseCase: LoadMovieDetailsUseCase,
    private val getMoviesFromCacheUseCase: GetMoviesFromCacheUseCase,
) : ViewModel() {

    val state = getStateUseCase(SCREEN_TYPE)

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            loadAllMoviesUseCase()
        }
    }

    fun onResume() {
        viewModelScope.launch {
            getMoviesFromCacheUseCase()
        }
    }

    fun loadOneMovie(movie: Movie) {
        viewModelScope.launch {
            loadMovieDetailsUseCase(movie, SCREEN_TYPE)
        }
    }

    companion object {
        private val SCREEN_TYPE = ScreenType.ALL_MOVIES
    }
}