package com.spinoza.movieskotlin.domain.model

sealed class MoviesState {
    object Loading : MoviesState()
    class Error(val value: String) : MoviesState()
    class Movies(val items: List<Movie>) : MoviesState()
    class OneMovieDetails(val value: MovieDetails) : MoviesState()
    class FavouriteStatus(val value: Boolean) : MoviesState()
}