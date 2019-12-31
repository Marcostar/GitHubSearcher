package com.example.githubsearcher.screens.repoScreen

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.githubsearcher.model.GithubUser
import com.example.githubsearcher.model.RepositoryData
import com.example.githubsearcher.model.UserDetails
import com.example.githubsearcher.network.GithubAPI
import kotlinx.coroutines.*

class RepositoryViewModel(userId: String) : ViewModel() {

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _getRepoList = MutableLiveData<List<RepositoryData>>()

    val getRepoList: LiveData<List<RepositoryData>>
        get() = _getRepoList



    private val _userDetails = MutableLiveData<UserDetails>()

    val userDetails: LiveData<UserDetails>
        get() = _userDetails

    init {
        getUserDetails(userId)
        getUserRepositories(userId)
    }


    fun search(query: String){
        if (query.isEmpty()||query.equals(""))
        {

        }
        else
        {

        }
    }

    private fun getUserRepositories(userId: String) {
        uiScope.launch {
            _getRepoList.value = getRepos(userId)
        }
    }

    private suspend fun getRepos(userId: String): List<RepositoryData>? {
        return withContext(Dispatchers.IO){
            var result = GithubAPI.retrofitService.getRepositories(userId).await()
            result
        }
    }

    private fun getUserDetails(userId: String) {
        uiScope.launch {
            _userDetails.value = getUserInfo(userId)
        }
    }

    private suspend fun getUserInfo(userId: String): UserDetails? {
        return withContext(Dispatchers.IO){
            var result = GithubAPI.retrofitService.getUserDetails(userId).await()
            result
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}

class RepositoryViewModelFactory(private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)) {
            return RepositoryViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
