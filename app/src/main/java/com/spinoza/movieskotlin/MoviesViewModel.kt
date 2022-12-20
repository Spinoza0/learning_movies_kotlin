package com.spinoza.movieskotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.movieskotlin.api.ApiFactory
import com.spinoza.movieskotlin.movies.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel(application: Application) : AndroidViewModel(application),
    MoviesListShowable {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val movies = MutableLiveData<MutableList<Movie>>()
    private val isLoading = MutableLiveData(false)
    private val compositeDisposable = CompositeDisposable()

    private var page = 1

    override fun getMovies(): LiveData<MutableList<Movie>> = movies
    override fun getIsLoading(): LiveData<Boolean> = isLoading

    init {
        loadMovies()
    }

    override fun loadMovies() {
        val loading = isLoading.value
        loading?.let {
            if (!it) {
                val disposable: Disposable = ApiFactory.apiService.loadMovies(page)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { isLoading.setValue(true) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate { isLoading.setValue(false) }
                    .subscribe({ moviesResponse ->
                        val loadedMovies =
                            movies.value ?: moviesResponse.movies.toMutableList()
                        movies.value?.let { loadedMovies.addAll(moviesResponse.movies) }
                        movies.value = loadedMovies
                        page++
                    }) { throwable -> Log.e(TAG, throwable.toString()) }
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}