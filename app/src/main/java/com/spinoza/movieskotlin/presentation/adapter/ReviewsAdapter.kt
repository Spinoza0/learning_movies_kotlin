package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.movieskotlin.databinding.ReviewItemBinding
import com.spinoza.movieskotlin.domain.model.Review

class ReviewsAdapter : ListAdapter<Review, ReviewViewHolder>(ReviewsDiffCallback()) {
    companion object {
        private const val TYPE_POSITIVE = "Позитивный"
        private const val TYPE_NEGATIVE = "Негативный"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ReviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        with(holder.binding) {
            textViewAuthor.text = review.author
            textViewReview.text = review.review
            val title = review.title
            if (title.isEmpty()) {
                textViewTitle.visibility = View.GONE
            } else {
                textViewTitle.text = title
            }
            val colorResId = when (review.type) {
                TYPE_POSITIVE -> android.R.color.holo_green_light
                TYPE_NEGATIVE -> android.R.color.holo_red_light
                else -> android.R.color.holo_orange_light
            }
            val color = ContextCompat.getColor(holder.itemView.context, colorResId)
            linearLayoutReviews.setBackgroundColor(color)
        }
    }
}