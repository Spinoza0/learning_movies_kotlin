package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.usecase.ChangeFavouriteStatusUseCase
import com.spinoza.movieskotlin.domain.usecase.GetStateUseCase
import com.spinoza.movieskotlin.domain.usecase.LoadOneMovieUseCase
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    getStateUseCase: GetStateUseCase,
    private val loadOneMovieUseCase: LoadOneMovieUseCase,
    private val changeFavouriteStatusUseCase: ChangeFavouriteStatusUseCase,
) : ViewModel() {

    val state = getStateUseCase()

    fun loadMovieDetails(movie: Movie) {
        viewModelScope.launch {
            loadOneMovieUseCase(movie)
        }
    }

    fun changeFavouriteStatus(movie: Movie) {
        viewModelScope.launch {
            changeFavouriteStatusUseCase(movie)
        }
    }
}