package com.example.githubsearcher.screens.repoScreen

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.githubsearcher.R
import com.example.githubsearcher.adapters.RepoClick
import com.example.githubsearcher.adapters.RepositoryListAdapter
import com.example.githubsearcher.databinding.GithubUsersFragmentBinding
import com.example.githubsearcher.databinding.RepositoryFragmentBinding
import com.example.githubsearcher.screens.userScreen.GithubUsersViewModel
import kotlinx.android.synthetic.main.repository_fragment.*

class RepositoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: RepositoryFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.repository_fragment, container, false)

        val viewModel = ViewModelProviders.of(this,RepositoryViewModelFactory(
            RepositoryFragmentArgs.fromBundle(arguments!!).userId))
            .get(RepositoryViewModel::class.java)

        binding.repoViewModel = viewModel

        val adapter = RepositoryListAdapter(RepoClick {

            val packageManager = context?.packageManager ?: return@RepoClick

            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))

            startActivity(intent)
        })


        binding.repoRecyclerView.adapter = adapter

        viewModel.getRepoList.observe(viewLifecycleOwner, Observer {
            it?.let {
               adapter.submitList(it)
            }
        })

        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.username.text = isNullOrEmpty(it.userId)
                binding.email.text = isNullOrEmpty(it.email)
                binding.location.text = isNullOrEmpty(it.location)
                binding.joinDate.text = isNullOrEmpty(it.join_date)
                binding.Followers.text = isNullOrEmpty(it.followers.toString())
                binding.following.text = isNullOrEmpty(it.following.toString())
                binding.bio.text = isNullOrEmpty(it.bio)
                Glide.with(imageView.context).load(it.imgSrcUrl).into(binding.imageView)
            }
        })

        binding.repoSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
             return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        binding.setLifecycleOwner(this)

        return binding.root
    }

    fun isNullOrEmpty(str: String?): String {
        if (str != null && !str.isEmpty())
            return str
        return "-"
    }

}
