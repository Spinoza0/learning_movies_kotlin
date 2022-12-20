package com.spinoza.movieskotlin.reviews

import androidx.recyclerview.widget.DiffUtil

class ReviewsDiffUtilCallback(
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