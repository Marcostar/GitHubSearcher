package com.example.githubsearcher.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.databinding.GithubUserRowBinding
import com.example.githubsearcher.model.GithubUser

class UserListAdapter : ListAdapter<GithubUser, UserListAdapter.ViewHolder>(UsersListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: GithubUserRowBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: GithubUser) {
            binding.userlist = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GithubUserRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class UsersListDiffCallback : DiffUtil.ItemCallback<GithubUser>() {

    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.userId == newItem.userId
    }


    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }


}