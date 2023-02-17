package com.spinoza.movieskotlin.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.movieskotlin.domain.model.Movie

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

