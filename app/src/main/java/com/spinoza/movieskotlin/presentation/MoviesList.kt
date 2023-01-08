package com.spinoza.movieskotlin.presentation

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.movieskotlin.domain.movies.Movie
import com.spinoza.movieskotlin.presentation.adapter.MoviesAdapter

class MoviesList(
    context: Context,
    moviesModel: MoviesListShowable,
    progressBar: ProgressBar,
    recyclerViewMovies: RecyclerView,
    onMovieClickListener: ((Movie) -> Unit),
    onReachEndListener: (() -> Unit),
) {
    init {
        val moviesAdapter = MoviesAdapter()
        recyclerViewMovies.adapter = moviesAdapter
        recyclerViewMovies.layoutManager = GridLayoutManager(context, 2)
        moviesAdapter.onMovieClickListener = onMovieClickListener
        moviesAdapter.onReachEndListener = onReachEndListener

        moviesModel
            .getMovies()
            .observe(context as LifecycleOwner, moviesAdapter::setMovies)
        moviesModel
            .getIsLoading()
            .observe(context as LifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
    }
}