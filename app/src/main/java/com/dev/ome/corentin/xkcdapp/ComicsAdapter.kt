package com.dev.ome.corentin.xkcdapp

import android.arch.paging.PagedListAdapter
import android.graphics.BitmapFactory
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.io.ByteArrayInputStream


class ComicsAdapter(private val presenter: MainPresenter) : PagedListAdapter<Comic, ComicsAdapter.ComicViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.comic_item, parent, false)
        return ComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = this.getItem(position)
        if (comic != null) holder.displayComic(comic)
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var itemName: TextView = itemView.findViewById(R.id.itemName)
        internal var itemThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)

        init {
            itemView.setOnClickListener(this)
        }

        fun displayComic(comic: Comic) {
            itemName.text = comic.safe_title
            if (comic.image != null) {
                itemThumbnail.setImageBitmap(BitmapFactory.decodeStream(ByteArrayInputStream(comic.image)))
            }
        }

        override fun onClick(v: View) {
            presenter.clickItem(this@ComicsAdapter.getItem(adapterPosition)!!)
        }
    }

    companion object {

        internal var diffCallback: DiffUtil.ItemCallback<Comic> = object : DiffUtil.ItemCallback<Comic>() {

            override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
                return oldItem.num == newItem.num
            }

            override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
                return oldItem == newItem
            }

        }
    }
}
