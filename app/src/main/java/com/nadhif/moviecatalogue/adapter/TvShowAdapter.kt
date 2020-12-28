package com.nadhif.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.databinding.ItemBinding
import com.nadhif.moviecatalogue.utils.Constants

class TvShowAdapter(private val clickListener: (FavoriteTvShow) -> Unit)
    : ListAdapter<FavoriteTvShow, TvShowAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<FavoriteTvShow>() {

        override fun areItemsTheSame(oldItem: FavoriteTvShow, newItem: FavoriteTvShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteTvShow, newItem: FavoriteTvShow): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: FavoriteTvShow, listener: (FavoriteTvShow) -> Unit) {
            with(binding) {
                val fullImageUrl = Constants.IMAGE_URL + tvShow.posterPath
                Glide.with(this.root)
                        .load(fullImageUrl)
                        .into(ivPoster)
                tvTitle.text = tvShow.name
                tvDesc.text = tvShow.overview
                root.setOnClickListener {
                    listener(tvShow)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = getItem(position)
        holder.bind(tvShow, clickListener)
    }

}