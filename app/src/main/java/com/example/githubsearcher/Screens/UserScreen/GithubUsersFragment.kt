package com.example.githubsearcher.Screens.UserScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.githubsearcher.R

class GithubUsersFragment : Fragment() {

    companion object {
        fun newInstance() =
            GithubUsersFragment()
    }

    private lateinit var viewModel: GithubUsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.github_users_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GithubUsersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
