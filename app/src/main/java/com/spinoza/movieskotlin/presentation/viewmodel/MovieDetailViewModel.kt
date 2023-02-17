package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    val state = moviesRepository.getState()

    fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            moviesRepository.getOneFavouriteMovie(id)
            moviesRepository.loadLinks(id)
            moviesRepository.loadReviews(id)
        }
    }

    fun changeFavouriteStatus(movie: Movie) {
        viewModelScope.launch {
            moviesRepository.changeFavouriteStatus(movie)
        }
    }
}