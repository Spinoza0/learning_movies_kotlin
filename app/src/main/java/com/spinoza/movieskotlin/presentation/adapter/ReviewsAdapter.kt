package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.movieskotlin.R
import com.spinoza.movieskotlin.domain.reviews.Review

class ReviewsAdapter : ListAdapter<Review, ReviewViewHolder>(ReviewsDiffCallback()) {
    companion object {
        private const val TYPE_POSITIVE = "Позитивный"
        private const val TYPE_NEGATIVE = "Негативный"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.textViewAuthor.text = review.author
        holder.textViewReview.text = review.review
        val title = review.title
        if (title == null || title.isEmpty()) {
            holder.textViewTitle.visibility = View.GONE
        } else {
            holder.textViewTitle.text = title
        }
        val colorResId = when (review.type) {
            TYPE_POSITIVE -> android.R.color.holo_green_light
            TYPE_NEGATIVE -> android.R.color.holo_red_light
            else -> android.R.color.holo_orange_light
        }
        val color = ContextCompat.getColor(holder.itemView.context, colorResId)
        holder.linearLayoutReviews.setBackgroundColor(color)
    }
}