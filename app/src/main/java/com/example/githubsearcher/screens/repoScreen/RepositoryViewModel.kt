package com.example.githubsearcher.screens.repoScreen

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.githubsearcher.model.GithubUser
import com.example.githubsearcher.model.RepositoryData
import com.example.githubsearcher.model.UserDetails
import com.example.githubsearcher.network.GithubAPI
import kotlinx.coroutines.*
import java.lang.Exception

enum class RestApiStatus { LOADING, ERROR, DONE }

class RepositoryViewModel(userId: String) : ViewModel() {

    private val _status = MutableLiveData<RestApiStatus>()

    val status: LiveData<RestApiStatus>
        get() = _status

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _originalRepoList = MutableLiveData<List<RepositoryData>>()

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

        if (query.isBlank())
            _getRepoList.postValue(_originalRepoList.value)
        else
        {
            _getRepoList.postValue(_originalRepoList.value?.filter {
                it.name.contains(query)
            })
        }

    }

    private fun getUserRepositories(userId: String) {
        uiScope.launch {
            _originalRepoList.value = getRepos(userId)
            _getRepoList.postValue(_originalRepoList.value)
        }
    }

    private suspend fun getRepos(userId: String): List<RepositoryData>? {
        return withContext(Dispatchers.IO){
            val result = GithubAPI.retrofitService.getRepositories(userId).await()
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
            val result = GithubAPI.retrofitService.getUserDetails(userId).await()
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
