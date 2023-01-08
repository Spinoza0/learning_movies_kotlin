package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.movieskotlin.R
import com.spinoza.movieskotlin.domain.reviews.Review

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {
    companion object {
        private const val TYPE_POSITIVE = "Позитивный"
        private const val TYPE_NEGATIVE = "Негативный"
    }

    private var reviews: List<Review> = ArrayList()

    fun setReviews(reviews: List<Review>) {
        val diffUtilCallback = DiffUtilCallback(this.reviews, reviews)
        val productDiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.reviews = reviews
        productDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
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

    override fun getItemCount() = reviews.size

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linearLayoutReviews: LinearLayout = itemView.findViewById(R.id.linearLayoutReviews)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewReview: TextView = itemView.findViewById(R.id.textViewReview)
    }

    private class DiffUtilCallback(
        private val oldList: List<Review>,
        private val newList: List<Review>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.author == newItem.author && oldItem.review == newItem.review
        }
    }
}