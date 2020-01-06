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

            val getDeferredRepoList = GithubAPI.retrofitService.getRepositories(userId)
            try{
                _status.value = RestApiStatus.LOADING
                val getList = getDeferredRepoList.await()
                _status.value = RestApiStatus.DONE
                _originalRepoList.value = getList

            }catch (e:Exception)
            {
                _status.value = RestApiStatus.ERROR
                _originalRepoList.value = ArrayList()
            }
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
//            _userDetails.value = getUserInfo(userId)
            val deferredUserData = GithubAPI.retrofitService.getUserDetails(userId)
            try{
                val getUser = deferredUserData.await()
                _userDetails.value = getUser

            }catch (e:Exception)
            {
                _userDetails.value = UserDetails("","","",0,0,"","","")
            }
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
