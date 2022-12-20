package com.spinoza.movieskotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.movieskotlin.databinding.ActivityFavouriteMoviesBinding
import com.spinoza.movieskotlin.movies.Movie
import com.spinoza.movieskotlin.movies.MoviesAdapter

class FavouriteMoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFavouriteMoviesBinding = ActivityFavouriteMoviesBinding
            .inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: FavouriteMoviesViewModel =
            ViewModelProvider(this)[FavouriteMoviesViewModel::class.java]

        MoviesList(
            this,
            viewModel,
            binding.progressBar,
            binding.recyclerViewMovies, object : MoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(movie: Movie) {
                    val intent: Intent = MovieDetailActivity.newIntent(
                        this@FavouriteMoviesActivity,
                        movie)
                    startActivity(intent)
                }
            }, object : MoviesAdapter.OnReachEndListener {
                override fun onReachEnd() {
                    viewModel.loadMovies()
                }
            }
        )
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FavouriteMoviesActivity::class.java)
        }
    }
}