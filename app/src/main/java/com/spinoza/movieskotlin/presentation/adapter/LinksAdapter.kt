package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.movieskotlin.R
import com.spinoza.movieskotlin.domain.links.Link

class LinksAdapter : RecyclerView.Adapter<LinksAdapter.LinkViewHolder>() {

    private var links: List<Link> = ArrayList()
    var onLinkClickListener: ((Link) -> Unit)? = null

    fun setLinks(links: List<Link>) {
        val diffUtilCallback = DiffUtilCallback(this.links, links)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.links = links
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.link_item,
            parent,
            false)
        return LinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        with(links[position]) {
            holder.textViewLinkName.text = name
            holder.itemView.setOnClickListener { onLinkClickListener?.invoke(this) }
        }
    }

    override fun getItemCount(): Int {
        return links.size
    }

    class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewLinkName: TextView = itemView.findViewById(R.id.textViewName)
    }

    private class DiffUtilCallback(
        private val oldList: List<Link>,
        private val newList: List<Link>,
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].url == newList[newItemPosition].url
        }
    }
}