package com.spinoza.movieskotlin.movies

import androidx.recyclerview.widget.DiffUtil

class MoviesDiffUtilCallback(
    private val oldList: List<Movie>,
    private val newList: List<Movie>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.year == newItem.year &&
                oldItem.name == newItem.name &&
                oldItem.description == newItem.description
    }
}