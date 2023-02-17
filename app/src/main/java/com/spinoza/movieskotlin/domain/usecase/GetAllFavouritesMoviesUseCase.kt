package com.spinoza.movieskotlin.domain.usecase

import com.spinoza.movieskotlin.domain.repository.MoviesRepository

class GetAllFavouritesMoviesUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke() = moviesRepository.getAllFavouriteMovies()
}