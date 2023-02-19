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
import com.spinoza.movieskotlin.domain.model.MovieDetails
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

    private fun parseParam(): MovieDetails {

        val movieDetails = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MOVIE, MovieDetails::class.java)
        } else {
            @Suppress("deprecation")
            intent.getParcelableExtra<MovieDetails>(EXTRA_MOVIE) as MovieDetails
        }
        movieDetails?.let { value ->
            return value
        }

        throw RuntimeException("Parameter MovieDetails not found in bundle")
    }

    private fun setupRecyclerView() {
        linksAdapter.onLinkClickListener = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        }
        binding.recyclerViewLinks.adapter = linksAdapter
        binding.recyclerViewReviews.adapter = reviewsAdapter
    }

    private fun setContent(movieDetails: MovieDetails) {
        with(binding) {
            Glide.with(this@MovieDetailActivity)
                .load(movieDetails.movie.poster)
                .into(imageViewPoster)
            textViewName.text = movieDetails.movie.name
            textViewYear.text = movieDetails.movie.year.toString()
            textViewDescription.text = movieDetails.movie.description
            setupImageViewStar(movieDetails.isFavourite)
            linksAdapter.submitList(movieDetails.links)
            reviewsAdapter.submitList(movieDetails.reviews)
            scrollView.parent.requestChildFocus(scrollView, scrollView)
        }

        setupObservers()
        setupListeners(movieDetails)
    }

    private fun setupListeners(movieDetails: MovieDetails) {
        binding.imageViewStar.setOnClickListener {
            viewModel.changeFavouriteStatus(movieDetails.movie)
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this@MovieDetailActivity) {
            when (it) {
                is MoviesState.Error -> {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        it.value,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is MoviesState.FavouriteStatus -> setupImageViewStar(it.value)
                else -> {}
            }
        }
    }

    private fun setupImageViewStar(status: Boolean) {
        val star = if (status) starOn else starOff
        binding.imageViewStar.setImageDrawable(star)
    }

    companion object {
        private const val EXTRA_MOVIE = "movie"

        fun newIntent(context: Context, movieDetails: MovieDetails): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movieDetails)
            }
        }
    }
}