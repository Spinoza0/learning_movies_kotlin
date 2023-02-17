package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.usecase.GetMoviesFromCacheUseCase
import com.spinoza.movieskotlin.domain.usecase.GetStateUseCase
import com.spinoza.movieskotlin.domain.usecase.LoadMoviesUseCase
import kotlinx.coroutines.launch

class MoviesViewModel(
    getStateUseCase: GetStateUseCase,
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val getMoviesFromCacheUseCase: GetMoviesFromCacheUseCase,
) : ViewModel() {

    val state = getStateUseCase()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            loadMoviesUseCase()
        }
    }

    fun onResume() {
        viewModelScope.launch {
            getMoviesFromCacheUseCase()
        }
    }
}