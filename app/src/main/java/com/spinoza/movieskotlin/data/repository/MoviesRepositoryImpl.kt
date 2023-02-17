package com.spinoza.movieskotlin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.movieskotlin.data.database.MovieDao
import com.spinoza.movieskotlin.data.mapper.MoviesMapper
import com.spinoza.movieskotlin.data.network.MoviesApiService
import com.spinoza.movieskotlin.domain.model.Movie
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
                    movies.addAll(moviesMapper.mapDtoToEntity(it.movies))
                }
                state.value = MoviesState.Movies(movies.toList())
            } else {
                state.value = getError(response)
            }
        }
    }

    override suspend fun loadLinks(movieId: Int) {
        val response = moviesApiService.loadLinks(movieId)
        if (response.isSuccessful) {
            response.body()?.let {
                state.value = MoviesState.Links(moviesMapper.mapDtoToEntity(it.linkItemsList.items))
            }
        }
    }

    override suspend fun loadReviews(movieId: Int) {
        val response = moviesApiService.loadReviews(movieId)
        if (response.isSuccessful) {
            response.body()?.let {
                state.value = MoviesState.Reviews(moviesMapper.mapDtoToEntity(it.reviews))
            }
        }
    }

    override suspend fun getAllFavouriteMovies() {
        runCatching {
            state.value = MoviesState.AllFavouriteMovies(
                moviesMapper.mapDbModelToEntity(movieDao.getAllFavouriteMovies())
            )
        }.onFailure {
            state.value = getError(it)
        }
    }

    override suspend fun getOneFavouriteMovie(movieId: Int) {
        runCatching {
            state.value = MoviesState.OneFavouriteMovie(
                moviesMapper.mapDbModelToEntity(movieDao.getFavouriteMovie(movieId))
            )
        }.onFailure {
            state.value = getError(it)
        }
    }

    override suspend fun changeFavouriteStatus(movie: Movie) {
        val newMovie = movie.copy(isFavourite = !movie.isFavourite)
        runCatching {
            if (newMovie.isFavourite) {
                movieDao.insertMovie(moviesMapper.mapEntityToDbModel(newMovie))
                state.value = MoviesState.FavouriteStatus(true)
            } else {
                movieDao.removeMovie(newMovie.id)
                state.value = MoviesState.FavouriteStatus(false)
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