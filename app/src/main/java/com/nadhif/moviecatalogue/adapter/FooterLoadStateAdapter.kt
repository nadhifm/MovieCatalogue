package com.nadhif.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.databinding.LoadStateFooterItemBinding

class FooterLoadStateAdapter(
    private val retry:() -> Unit
) : LoadStateAdapter<FooterLoadStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder.create(parent, retry)
    }

    class ViewHolder(
        private val binding: LoadStateFooterItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.load_state_footer_item, parent, false)
                val binding = LoadStateFooterItemBinding.bind(view)
                return ViewHolder(binding, retry)
            }

        }
    }
}