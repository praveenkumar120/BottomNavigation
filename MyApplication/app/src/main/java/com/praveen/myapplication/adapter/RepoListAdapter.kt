package com.praveen.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.praveen.myapplication.data.ItemsItem
import com.praveen.myapplication.databinding.ItemRepoListBinding

class RepoListAdapter(
    private val mCallBack: RepoItemClick
) :
    PagingDataAdapter<ItemsItem, RepoListAdapter.RepoListViewHolder>(RepoListDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoListViewHolder {
        return RepoListViewHolder(
            ItemRepoListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), mCallBack
        )
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class RepoListViewHolder(
        private val binding: ItemRepoListBinding,
        private val repoItemClick: RepoItemClick
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            var commentsList = ""
            binding.data = data
            binding.itemView.setOnClickListener {
                if (binding.etEnterComments.text?.isNotEmpty() == true) {
                    commentsList = (binding.etEnterComments.text.toString())
                }
                repoItemClick.onRepoItemClick(commentsList,absoluteAdapterPosition)
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

    interface RepoItemClick {
        fun onRepoItemClick(userComments: String,position: Int)
    }
}