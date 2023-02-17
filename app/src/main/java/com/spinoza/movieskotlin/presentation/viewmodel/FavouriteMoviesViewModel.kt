package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.usecase.GetAllFavouritesMoviesUseCase
import com.spinoza.movieskotlin.domain.usecase.GetStateUseCase
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel(
    getStateUseCase: GetStateUseCase,
    private val getAllFavouritesMoviesUseCase: GetAllFavouritesMoviesUseCase,
) : ViewModel() {

    val state = getStateUseCase()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            getAllFavouritesMoviesUseCase()
        }
    }
}