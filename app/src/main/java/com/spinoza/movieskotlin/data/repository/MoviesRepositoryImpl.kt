package com.spinoza.movieskotlin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.movieskotlin.data.database.MovieDao
import com.spinoza.movieskotlin.data.mapper.MoviesMapper
import com.spinoza.movieskotlin.data.network.MoviesApiService
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.MovieDetails
import com.spinoza.movieskotlin.domain.model.MoviesState
import com.spinoza.movieskotlin.domain.model.ScreenType
import com.spinoza.movieskotlin.domain.repository.MoviesRepository
import retrofit2.Response

class MoviesRepositoryImpl(
    private val moviesApiService: MoviesApiService,
    private val movieDao: MovieDao,
    private val moviesMapper: MoviesMapper,
) : MoviesRepository {

    private var page = FIRST_PAGE
    private val allMovies = mutableListOf<Movie>()

    private val stateAllMovies = MutableLiveData<MoviesState>()
    private val stateFavouriteMovies = MutableLiveData<MoviesState>()
    private val stateMovieDetails = MutableLiveData<MoviesState>()

    override fun getState(screenType: ScreenType): LiveData<MoviesState> = when (screenType) {
        ScreenType.ALL_MOVIES -> stateAllMovies
        ScreenType.FAVOURITE_MOVIES -> stateFavouriteMovies
        ScreenType.MOVIE_DETAILS -> stateMovieDetails
    }

    override suspend fun resetState(screenType: ScreenType) = when (screenType) {
        ScreenType.ALL_MOVIES -> stateAllMovies.value = MoviesState.Movies(allMovies.toList())
        ScreenType.FAVOURITE_MOVIES -> getAllFavouriteMovies()
        ScreenType.MOVIE_DETAILS -> stateMovieDetails.value = MoviesState.Empty
    }

    override suspend fun loadAllMovies() {
        stateAllMovies.value = MoviesState.Loading
        runCatching {
            val response = moviesApiService.loadMovies(page)
            if (response.isSuccessful) {
                page++
                response.body()?.let {
                    allMovies.addAll(moviesMapper.mapMoviesDtoToEntity(it.movies))
                }
                stateAllMovies.value = MoviesState.Movies(allMovies.toList())
            } else {
                stateAllMovies.value = getError(response)
            }
        }.onFailure {
            stateAllMovies.value = getError(it)
        }
    }

    override suspend fun getAllFavouriteMovies() {
        runCatching {
            stateFavouriteMovies.value = MoviesState.FavouriteMovies(
                moviesMapper.mapDbModelToEntity(movieDao.getAllFavouriteMovies())
            )
        }.onFailure {
            stateFavouriteMovies.value = getError(it)
        }
    }

    override suspend fun loadMovieDetails(movie: Movie, screenType: ScreenType) {
        when (screenType) {
            ScreenType.ALL_MOVIES -> stateAllMovies.value = MoviesState.Loading
            ScreenType.FAVOURITE_MOVIES -> stateFavouriteMovies.value = MoviesState.Loading
            else -> {}
        }

        var oneMovieDetails = MoviesState.OneMovieDetails(
            MovieDetails(
                movie,
                isMovieFavourite(movie.id),
                listOf(),
                listOf()
            )
        )

        runCatching {
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

                oneMovieDetails = MoviesState.OneMovieDetails(
                    MovieDetails(
                        newMovie,
                        isMovieFavourite(newMovie.id),
                        links,
                        reviews
                    )
                )
            }
        }

        when (screenType) {
            ScreenType.ALL_MOVIES -> stateAllMovies.value = oneMovieDetails
            ScreenType.FAVOURITE_MOVIES -> stateFavouriteMovies.value = oneMovieDetails
            else -> {}
        }
    }

    private suspend fun isMovieFavourite(movieId: Int): Boolean = try {
        movieDao.isMovieFavourite(movieId)
    } catch (e: Exception) {
        false
    }

    override suspend fun changeFavouriteStatus(movie: Movie) {
        runCatching {
            val status = if (movieDao.isMovieFavourite(movie.id)) {
                movieDao.removeMovie(movie.id)
                MoviesState.FavouriteStatus(false)
            } else {
                movieDao.insertMovie(moviesMapper.mapEntityToDbModel(movie))
                MoviesState.FavouriteStatus(true)
            }
            stateMovieDetails.value = status
            stateFavouriteMovies.value = status
        }.onFailure {
            stateMovieDetails.value = getError(it)
        }
        resetState(ScreenType.MOVIE_DETAILS)
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