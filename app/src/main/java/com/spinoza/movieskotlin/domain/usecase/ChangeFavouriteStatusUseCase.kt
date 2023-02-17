package com.spinoza.movieskotlin.domain.usecase

import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.repository.MoviesRepository

class ChangeFavouriteStatusUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movie: Movie) = moviesRepository.changeFavouriteStatus(movie)
}