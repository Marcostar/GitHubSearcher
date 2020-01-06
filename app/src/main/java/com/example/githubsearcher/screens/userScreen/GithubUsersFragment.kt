package com.example.githubsearcher.screens.userScreen

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.githubsearcher.R
import com.example.githubsearcher.adapters.UserClick
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



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.toString())
                return false
            }

        })


        binding.searchView.setOnCloseListener {
            binding.searchView.clearFocus()
            true
        }


        binding.searchView.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                hideKeyboard()
            }
        }

        binding.setLifecycleOwner(this)

            return binding.root
        }


    fun Fragment.hideKeyboard(){
        view?.let {
            activity?.hideKeyBoard(it)
        }
    }


    fun Context.hideKeyBoard(view: View){
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
