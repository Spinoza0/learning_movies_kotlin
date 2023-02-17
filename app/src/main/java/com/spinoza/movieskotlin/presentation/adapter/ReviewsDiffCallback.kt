package com.spinoza.movieskotlin.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.movieskotlin.domain.model.Review

class ReviewsDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}

