package com.spinoza.movieskotlin.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import com.spinoza.movieskotlin.R
import com.spinoza.movieskotlin.databinding.ActivityMoviesBinding
import com.spinoza.movieskotlin.domain.model.MovieDetails
import com.spinoza.movieskotlin.domain.model.MoviesState
import com.spinoza.movieskotlin.presentation.adapter.MoviesAdapter
import com.spinoza.movieskotlin.presentation.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity() {

    private val viewModel by viewModel<MoviesViewModel>()

    private val binding by lazy {
        ActivityMoviesBinding.inflate(
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
                is MoviesState.Movies -> moviesAdapter.submitList(it.items)
                is MoviesState.OneMovieDetails -> showMoviesDetails(it.value)
                else -> {}
            }
        }
    }

    private fun setupRecyclerView(binding: ActivityMoviesBinding) {
        binding.recyclerViewMovies.adapter = moviesAdapter
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewMovies.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_DRAGGING && moviesAdapter.itemCount == EMPTY_LIST)
                    viewModel.loadMovies()
            }
        })
        moviesAdapter.onMovieClickListener = { viewModel.loadOneMovie(it) }
        moviesAdapter.onReachEndListener = { viewModel.loadMovies() }
    }

    private fun showMoviesDetails(movieDetails: MovieDetails) {
        val intent = MovieDetailActivity.newIntent(
            this@MoviesActivity,
            movieDetails
        )
        startActivity(intent)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.resetState()
    }

    companion object {
        private const val EMPTY_LIST = 0
    }
}