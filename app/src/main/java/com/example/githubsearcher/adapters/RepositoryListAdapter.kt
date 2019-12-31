package com.example.githubsearcher.adapters

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.databinding.RepositoryRowBinding
import com.example.githubsearcher.model.RepositoryData


class RepositoryListAdapter(val clickListener: RepoClick) : ListAdapter<RepositoryData, RepositoryListAdapter.ViewHolder>(RepositoryDataDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RepositoryRowBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: RepositoryData, clickListener: RepoClick) {
            binding.repository = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepositoryRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class RepositoryDataDiffCallback : DiffUtil.ItemCallback<RepositoryData>() {

    override fun areItemsTheSame(oldItem: RepositoryData, newItem: RepositoryData): Boolean {
        return oldItem.name == newItem.name
    }


    override fun areContentsTheSame(oldItem: RepositoryData, newItem: RepositoryData): Boolean {
        return oldItem == newItem
    }


}

class RepoClick(val row: (RepositoryData) -> Unit) {

    fun onClick(repo: RepositoryData) = row(repo)
}