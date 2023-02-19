package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.spinoza.movieskotlin.databinding.MovieItemBinding
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.presentation.utils.setupTextViewRating

class MoviesAdapter : ListAdapter<Movie, MovieViewHolder>(MoviesDiffCallback()) {
    var onReachEndListener: (() -> Unit)? = null
    var onMovieClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                Glide.with(itemView).load(poster).into(binding.imageViewPoster)
                setupTextViewRating(itemView.context, binding.textViewRating, this)
                if (position >= itemCount - 10) {
                    onReachEndListener?.invoke()
                }
                itemView.setOnClickListener { onMovieClickListener?.invoke(this) }
            }
        }
    }
}