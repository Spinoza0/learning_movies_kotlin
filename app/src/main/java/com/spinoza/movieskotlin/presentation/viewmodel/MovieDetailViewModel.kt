package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.ScreenType
import com.spinoza.movieskotlin.domain.usecase.ChangeFavouriteStatusUseCase
import com.spinoza.movieskotlin.domain.usecase.GetStateUseCase
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    getStateUseCase: GetStateUseCase,
    private val changeFavouriteStatusUseCase: ChangeFavouriteStatusUseCase,
) : ViewModel() {

    val state = getStateUseCase(ScreenType.MOVIE_DETAILS)

    fun changeFavouriteStatus(movie: Movie) {
        viewModelScope.launch {
            changeFavouriteStatusUseCase(movie)
        }
    }
}