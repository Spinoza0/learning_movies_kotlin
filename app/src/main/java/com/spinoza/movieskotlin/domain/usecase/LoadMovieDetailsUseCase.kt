package com.spinoza.movieskotlin.domain.usecase

import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.ScreenType
import com.spinoza.movieskotlin.domain.repository.MoviesRepository

class LoadMovieDetailsUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movie: Movie, screenType: ScreenType) =
        moviesRepository.loadMovieDetails(movie, screenType)
}