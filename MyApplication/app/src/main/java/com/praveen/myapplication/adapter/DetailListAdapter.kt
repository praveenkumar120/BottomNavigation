package com.praveen.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.praveen.myapplication.data.ItemsItem
import com.praveen.myapplication.databinding.ItemDetailedListBinding

class DetailListAdapter(private val userComments: String, private var commentPos: Int) :
    PagingDataAdapter<ItemsItem, DetailListAdapter.DetailListViewHolder>(RepoListDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailListViewHolder {
        return DetailListViewHolder(
            ItemDetailedListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class DetailListViewHolder(
        private val binding: ItemDetailedListBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            binding.data = data
            if (userComments.isNotEmpty() && commentPos == absoluteAdapterPosition) {
                binding.tvComments.visibility = View.VISIBLE
                binding.tvUserComments.text = userComments
                binding.tvUserComments.visibility = View.VISIBLE
            } else {
                binding.tvComments.visibility = View.GONE
                binding.tvUserComments.text = userComments
                binding.tvUserComments.visibility = View.GONE
            }
        }
    }

    object RepoListDiffUtil : DiffUtil.ItemCallback<ItemsItem>() {
        override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem == newItem
        }
    }
}