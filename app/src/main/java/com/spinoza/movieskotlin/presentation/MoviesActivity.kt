package com.spinoza.movieskotlin.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.movieskotlin.R
import com.spinoza.movieskotlin.data.MoviesApiFactory
import com.spinoza.movieskotlin.databinding.ActivityMoviesBinding
import com.spinoza.movieskotlin.presentation.viewmodel.MoviesViewModel
import com.spinoza.movieskotlin.presentation.viewmodel.MoviesViewModelFactory

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMoviesBinding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            this,
            MoviesViewModelFactory(MoviesApiFactory.apiService)
        )[MoviesViewModel::class.java]

        MoviesList(
            this,
            viewModel,
            binding.progressBar,
            binding.recyclerViewMovies,
            {
                val intent = MovieDetailActivity.newIntent(
                    this@MoviesActivity,
                    it)
                startActivity(intent)
            },
            { viewModel.loadMovies() }
        )

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
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