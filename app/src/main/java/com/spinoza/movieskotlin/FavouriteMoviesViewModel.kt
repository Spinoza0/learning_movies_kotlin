package com.spinoza.movieskotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.movieskotlin.database.MovieDao
import com.spinoza.movieskotlin.database.MovieDatabase
import com.spinoza.movieskotlin.movies.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application),
    MoviesListShowable {
    private val movieDao: MovieDao
    private val compositeDisposable = CompositeDisposable()
    private val isLoading = MutableLiveData<Boolean>()

    init {
        movieDao = MovieDatabase.getInstance(application).movieDao()
    }

    override fun getMovies(): LiveData<MutableList<Movie>> = movieDao.getAllFavouriteMovies()
    override fun getIsLoading(): LiveData<Boolean> = isLoading
    override fun loadMovies() {}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}