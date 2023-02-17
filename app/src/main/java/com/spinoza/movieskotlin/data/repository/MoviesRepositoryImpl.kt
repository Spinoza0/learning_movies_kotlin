package com.spinoza.movieskotlin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.movieskotlin.data.database.MovieDao
import com.spinoza.movieskotlin.data.mapper.MoviesMapper
import com.spinoza.movieskotlin.data.network.MoviesApiService
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.MovieDetails
import com.spinoza.movieskotlin.domain.model.MoviesState
import com.spinoza.movieskotlin.domain.repository.MoviesRepository
import retrofit2.Response

class MoviesRepositoryImpl(
    private val moviesApiService: MoviesApiService,
    private val movieDao: MovieDao,
    private val moviesMapper: MoviesMapper,
) : MoviesRepository {

    private var page = FIRST_PAGE
    private val movies = mutableListOf<Movie>()

    private val state = MutableLiveData<MoviesState>()
    override fun getState(): LiveData<MoviesState> = state

    override suspend fun loadMovies() {
        if (state.value != MoviesState.Loading) {
            state.value = MoviesState.Loading
            val response = moviesApiService.loadMovies(page)
            if (response.isSuccessful) {
                page++
                response.body()?.let {
                    movies.addAll(moviesMapper.mapMoviesDtoToEntity(it.movies))
                }
                state.value = MoviesState.Movies(movies.toList())
            } else {
                state.value = getError(response)
            }
        }
    }

    override suspend fun getAllFavouriteMovies() {
        runCatching {
            state.value = MoviesState.FavouriteMovies(
                moviesMapper.mapDbModelToEntity(movieDao.getAllFavouriteMovies())
            )
        }.onFailure {
            state.value = getError(it)
        }
    }

    override suspend fun loadOneMovie(movie: Movie) {
        val oneMovieResponse = moviesApiService.loadOneMovie(movie.id)
        if (oneMovieResponse.isSuccessful) {
            var newMovie = movie
            oneMovieResponse.body()?.let {
                newMovie = moviesMapper.mapDtoToEntity(it)
            }

            val links = moviesMapper.mapLinksDtoToEntity(
                oneMovieResponse.body()?.linkItemsList?.items ?: listOf()
            )

            val reviewsResponse = moviesApiService.loadReviews(movie.id)
            val reviews = if (reviewsResponse.isSuccessful) {
                moviesMapper.mapReviewsDtoToEntity(reviewsResponse.body()?.reviews ?: listOf())
            } else {
                listOf()
            }

            state.value = MoviesState.OneMovieDetails(
                MovieDetails(
                    newMovie,
                    isMovieFavourite(newMovie.id),
                    links,
                    reviews
                )
            )
        } else {
            state.value = MoviesState.OneMovieDetails(
                MovieDetails(
                    movie,
                    isMovieFavourite(movie.id),
                    listOf(),
                    listOf()
                )
            )
        }
    }

    private suspend fun isMovieFavourite(movieId: Int): Boolean = try {
        movieDao.isMovieFavourite(movieId)
    } catch (e: Exception) {
        false
    }

    override suspend fun changeFavouriteStatus(movie: Movie) {
        runCatching {
            if (movieDao.isMovieFavourite(movie.id)) {
                movieDao.removeMovie(movie.id)
                state.value = MoviesState.FavouriteStatus(false)
            } else {
                movieDao.insertMovie(moviesMapper.mapEntityToDbModel(movie))
                state.value = MoviesState.FavouriteStatus(true)
            }
        }.onFailure {
            state.value = getError(it)
        }
    }

    private fun <T> getError(response: Response<T>): MoviesState.Error {
        val errorBody = response.errorBody()?.string() ?: ""
        return MoviesState.Error("${response.code()} $errorBody")
    }

    private fun getError(e: Throwable): MoviesState.Error =
        MoviesState.Error(e.localizedMessage ?: e.message ?: e.toString())

    companion object {
        private const val FIRST_PAGE = 1
    }
}