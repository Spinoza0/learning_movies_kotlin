package com.spinoza.movieskotlin.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.spinoza.movieskotlin.databinding.ActivityFavouriteMoviesBinding
import com.spinoza.movieskotlin.domain.model.MovieDetails
import com.spinoza.movieskotlin.domain.model.MoviesState
import com.spinoza.movieskotlin.presentation.adapter.MoviesAdapter
import com.spinoza.movieskotlin.presentation.viewmodel.FavouriteMoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteMoviesActivity : AppCompatActivity() {

    private val viewModel by viewModel<FavouriteMoviesViewModel>()

    private val binding by lazy {
        ActivityFavouriteMoviesBinding.inflate(
            layoutInflater
        )
    }
    private val moviesAdapter by lazy { MoviesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView(binding)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this) {
            if (it !is MoviesState.Loading) {
                binding.progressBar.visibility = View.GONE
            }

            when (it) {
                is MoviesState.Error -> Toast.makeText(
                    this,
                    it.value,
                    Toast.LENGTH_LONG
                ).show()
                is MoviesState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is MoviesState.FavouriteMovies -> moviesAdapter.submitList(it.items)
                is MoviesState.FavouriteStatus -> viewModel.loadAllMovies()
                is MoviesState.OneMovieDetails -> showMoviesDetails(it.value)
                else -> {}
            }
        }
    }

    private fun setupRecyclerView(binding: ActivityFavouriteMoviesBinding) {
        binding.recyclerViewMovies.adapter = moviesAdapter
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
        moviesAdapter.onMovieClickListener = { viewModel.loadMovieDetails(it) }
    }

    private fun showMoviesDetails(movieDetails: MovieDetails) {
        val intent = MovieDetailActivity.newIntent(
            this@FavouriteMoviesActivity,
            movieDetails
        )
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.resetState()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FavouriteMoviesActivity::class.java)
        }
    }
}