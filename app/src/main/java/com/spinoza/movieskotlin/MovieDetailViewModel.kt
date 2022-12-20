package com.spinoza.movieskotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.movieskotlin.api.ApiFactory
import com.spinoza.movieskotlin.database.MovieDao
import com.spinoza.movieskotlin.database.MovieDatabase
import com.spinoza.movieskotlin.links.Link
import com.spinoza.movieskotlin.movies.Movie
import com.spinoza.movieskotlin.reviews.Review
import com.spinoza.movieskotlin.reviews.ReviewsResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "MovieDetailViewModel"
    }

    private val compositeDisposable = CompositeDisposable()
    private val links: MutableLiveData<List<Link>> = MutableLiveData<List<Link>>()
    private val reviews: MutableLiveData<List<Review>> = MutableLiveData<List<Review>>()
    private val movieDao: MovieDao

    init {
        movieDao = MovieDatabase.getInstance(application).movieDao()
    }

    fun getFavouriteMovie(id: Int) = movieDao.getFavouriteMovie(id)
    fun getReviews(): LiveData<List<Review>> = reviews
    fun getLinks(): LiveData<List<Link>> = links

    fun loadLinks(id: Int) {
        val disposable: Disposable = ApiFactory.apiService.loadLinks(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { linkResponse -> linkResponse.linkItemsList.items }
            .subscribe(links::setValue) { throwable -> Log.d(TAG, throwable.toString()) }
        compositeDisposable.add(disposable)
    }

    fun loadReviews(id: Int) {
        val disposable: Disposable = ApiFactory.apiService.loadReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(ReviewsResponse::reviews)
            .subscribe(reviews::setValue
            ) { throwable -> Log.e(TAG, throwable.toString()) }
        compositeDisposable.add(disposable)
    }

    fun insertMovie(movie: Movie) {
        val disposable: Disposable = movieDao.insertMovie(movie)
            .subscribeOn(Schedulers.io())
            .subscribe({}) { throwable -> Log.e(TAG, throwable.toString()) }
        compositeDisposable.add(disposable)
    }

    fun removeMovie(movieId: Int) {
        val disposable: Disposable = movieDao.removeMovie(movieId)
            .subscribeOn(Schedulers.io())
            .subscribe({}) { throwable -> Log.e(TAG, throwable.toString()) }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}