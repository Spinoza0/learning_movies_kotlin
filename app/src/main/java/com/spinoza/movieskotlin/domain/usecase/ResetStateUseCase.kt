package com.spinoza.movieskotlin.domain.usecase

import com.spinoza.movieskotlin.domain.model.ScreenType
import com.spinoza.movieskotlin.domain.repository.MoviesRepository

class ResetStateUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(screenType: ScreenType) = moviesRepository.resetState(screenType)
}