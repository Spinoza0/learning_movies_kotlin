package com.spinoza.movieskotlin.links

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.movieskotlin.R

class LinksAdapter : RecyclerView.Adapter<LinksAdapter.LinkViewHolder>() {

    private var links: List<Link> = ArrayList()
    var onLinkClickListener: OnLinkClickListener? = null

    fun setLinks(links: List<Link>) {
        val diffUtilCallback = LinksDiffUtilCallback(this.links, links)
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
        val link = links[position]
        holder.textViewLinkName.text = link.name
        holder.itemView.setOnClickListener { onLinkClickListener?.onLinkClick(link) }
    }

    override fun getItemCount(): Int {
        return links.size
    }

    interface OnLinkClickListener {
        fun onLinkClick(link: Link)
    }

    class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewLinkName: TextView

        init {
            textViewLinkName = itemView.findViewById(R.id.textViewName)
        }
    }
}