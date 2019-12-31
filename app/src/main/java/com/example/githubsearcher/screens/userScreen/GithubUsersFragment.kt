package com.example.githubsearcher.screens.userScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.githubsearcher.R
import com.example.githubsearcher.adapters.UserListAdapter
import com.example.githubsearcher.databinding.GithubUsersFragmentBinding

class GithubUsersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: GithubUsersFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.github_users_fragment, container, false)

        val viewModel = ViewModelProviders.of(this)
                .get(GithubUsersViewModel::class.java)

        binding.githubUserViewModel = viewModel

        val adapter = UserListAdapter()

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
