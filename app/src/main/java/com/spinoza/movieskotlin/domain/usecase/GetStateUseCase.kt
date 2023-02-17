package com.spinoza.movieskotlin.domain.usecase

import com.spinoza.movieskotlin.domain.repository.MoviesRepository

class GetStateUseCase(private val moviesRepository: MoviesRepository) {
    operator fun invoke() = moviesRepository.getState()
}