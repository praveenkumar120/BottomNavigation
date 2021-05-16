package com.praveen.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.praveen.myapplication.databinding.ItemProgressBinding

class RepoLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RepoLoadStateAdapter.RepoLoadStateViewHolder>() {

    inner class RepoLoadStateViewHolder(
        private val binding: ItemProgressBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }
            if (loadState is LoadState.Loading) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
            if (loadState is LoadState.Error) {
                binding.buttonRetry.visibility = View.VISIBLE
                binding.textViewError.visibility = View.VISIBLE
            } else {
                binding.buttonRetry.visibility = View.GONE
                binding.textViewError.visibility = View.GONE
            }
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: RepoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = RepoLoadStateViewHolder(
        ItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}