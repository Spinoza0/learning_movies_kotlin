package com.spinoza.movieskotlin.domain.model

sealed class MoviesState {
    object Loading : MoviesState()
    class Error(val value: String) : MoviesState()
    class Movies(val items: List<Movie>) : MoviesState()
    class AllFavouriteMovies(val items: List<Movie>) : MoviesState()
    class OneFavouriteMovie(val value: Movie) : MoviesState()
    class FavouriteStatus(val value: Boolean) : MoviesState()
    class Links(val items: List<Link>) : MoviesState()
    class Reviews(val items: List<Review>) : MoviesState()
}