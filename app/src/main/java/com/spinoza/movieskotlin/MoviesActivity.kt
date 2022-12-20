package com.spinoza.movieskotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.movieskotlin.databinding.ActivityMoviesBinding
import com.spinoza.movieskotlin.movies.Movie
import com.spinoza.movieskotlin.movies.MoviesAdapter

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMoviesBinding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        MoviesList(
            this,
            viewModel,
            binding.progressBar,
            binding.recyclerViewMovies, object : MoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(movie: Movie) {
                    val intent = MovieDetailActivity.newIntent(
                        this@MoviesActivity,
                        movie)
                    startActivity(intent)
                }
            },
            object : MoviesAdapter.OnReachEndListener {
                override fun onReachEnd() {
                    viewModel.loadMovies()
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemFavourite) {
            val intent = FavouriteMoviesActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}