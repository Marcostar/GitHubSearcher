package com.example.githubsearcher.screens.userScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearcher.model.GithubUser
import com.example.githubsearcher.network.GithubAPI
import kotlinx.coroutines.*

class GithubUsersViewModel : ViewModel() {


    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _getUserList = MutableLiveData<List<GithubUser>>()

    val getUserList: LiveData<List<GithubUser>>
        get() = _getUserList

    init {
        downloadUserList()
    }

    private fun downloadUserList() {
        uiScope.launch {
            _getUserList.value = downloadUserListFromServer()
        }
    }

    private suspend fun downloadUserListFromServer(): List<GithubUser>? {
        return withContext(Dispatchers.IO){
            var resultList = GithubAPI.retrofitService.getUsersList().await()
            resultList
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
