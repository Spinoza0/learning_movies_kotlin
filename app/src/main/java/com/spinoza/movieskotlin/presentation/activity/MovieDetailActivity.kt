package com.spinoza.movieskotlin.presentation.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.spinoza.movieskotlin.databinding.ActivityMovieDetailBinding
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.MoviesState
import com.spinoza.movieskotlin.presentation.adapter.LinksAdapter
import com.spinoza.movieskotlin.presentation.adapter.ReviewsAdapter
import com.spinoza.movieskotlin.presentation.viewmodel.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<MovieDetailViewModel>()

    private val binding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }
    private val linksAdapter by lazy { LinksAdapter() }
    private val reviewsAdapter by lazy { ReviewsAdapter() }
    private val starOff by lazy {
        ContextCompat.getDrawable(this@MovieDetailActivity, android.R.drawable.star_big_off)
    }
    private val starOn by lazy {
        ContextCompat.getDrawable(this@MovieDetailActivity, android.R.drawable.star_big_on)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        if (intent.hasExtra(EXTRA_MOVIE)) {
            setContent(parseParam())
        } else {
            finish()
        }
    }

    private fun parseParam(): Movie {

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
        } else {
            @Suppress("deprecation")
            intent.getParcelableExtra<Movie>(EXTRA_MOVIE) as Movie
        }
        movie?.let { value ->
            return value
        }

        throw RuntimeException("Parameter FILM not found in bundle")
    }

    private fun setupRecyclerView() {
        linksAdapter.onLinkClickListener = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        }
        binding.recyclerViewLinks.adapter = linksAdapter
        binding.recyclerViewReviews.adapter = reviewsAdapter
    }

    private fun setContent(movie: Movie) {
        setupObservers()
        setupListeners(movie)
        viewModel.loadMovieDetails(movie)
    }

    private fun setupListeners(movie: Movie) {
        binding.imageViewStar.setOnClickListener { viewModel.changeFavouriteStatus(movie) }
    }

    private fun setupObservers() {
        with(binding) {
            viewModel.state.observe(this@MovieDetailActivity) {
                when (it) {
                    is MoviesState.Error -> {
                        Toast.makeText(
                            this@MovieDetailActivity,
                            it.value,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is MoviesState.OneMovieDetails -> {
                        Glide.with(this@MovieDetailActivity)
                            .load(it.value.movie.poster)
                            .into(imageViewPoster)
                        textViewName.text = it.value.movie.name
                        textViewYear.text = it.value.movie.year.toString()
                        textViewDescription.text = it.value.movie.description
                        setupImageViewStar(it.value.isFavourite)
                        linksAdapter.submitList(it.value.links)
                        reviewsAdapter.submitList(it.value.reviews)
                    }
                    is MoviesState.FavouriteStatus -> setupImageViewStar(it.value)
                    else -> {}
                }
            }
        }
    }

    private fun setupImageViewStar(status: Boolean) {
        val star = if (status) starOn else starOff
        binding.imageViewStar.setImageDrawable(star)
    }

    companion object {
        private const val EXTRA_MOVIE = "movie"

        fun newIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movie)
            }
        }
    }
}