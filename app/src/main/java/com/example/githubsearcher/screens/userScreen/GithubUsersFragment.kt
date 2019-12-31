package com.example.githubsearcher.screens.userScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.githubsearcher.R
import com.example.githubsearcher.adapters.UserClick
import com.example.githubsearcher.adapters.UserListAdapter
import com.example.githubsearcher.databinding.GithubUsersFragmentBinding
import com.example.githubsearcher.model.GithubUser

class GithubUsersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: GithubUsersFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.github_users_fragment, container, false)

        val viewModel = ViewModelProviders.of(this)
                .get(GithubUsersViewModel::class.java)

        binding.githubUserViewModel = viewModel

        val adapter = UserListAdapter(UserClick {
            this.findNavController()
                .navigate(GithubUsersFragmentDirections.actionGithubUsersFragmentToRepositoryFragment(it.userId))
        })

        binding.githubUsersList.adapter = adapter

        viewModel.getUserList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }

}
