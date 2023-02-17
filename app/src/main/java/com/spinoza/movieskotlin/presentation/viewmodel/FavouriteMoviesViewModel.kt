package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.repository.MoviesRepository
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val state = moviesRepository.getState()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            moviesRepository.getAllFavouriteMovies()
        }
    }
}