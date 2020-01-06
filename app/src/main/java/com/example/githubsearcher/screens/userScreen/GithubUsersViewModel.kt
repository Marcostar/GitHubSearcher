package com.example.githubsearcher.screens.userScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearcher.model.GithubUser
import com.example.githubsearcher.network.GithubAPI
import com.example.githubsearcher.screens.repoScreen.RestApiStatus
import kotlinx.coroutines.*
import java.lang.Exception

enum class RestApiStatus { LOADING, ERROR, DONE }

class GithubUsersViewModel : ViewModel() {

    private val _status = MutableLiveData<RestApiStatus>()

    val status: LiveData<RestApiStatus>
        get() = _status

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _originalUserList =  MutableLiveData<List<GithubUser>>()

    private val _getUserList = MutableLiveData<List<GithubUser>>()

    val getUserList: LiveData<List<GithubUser>>
        get() = _getUserList



    init {
        downloadUserList()
    }


    fun search(query: String){

            if (query.isBlank())
                _getUserList.postValue(_originalUserList.value)
            else
            {
                _getUserList.postValue(_originalUserList.value?.filter {
                    it.userId.contains(query)
                })
            }


    }




    private fun downloadUserList() {
        uiScope.launch {
            val getUserListDeferred = GithubAPI.retrofitService.getUsersList()
            try {
                _status.value = RestApiStatus.LOADING
                val listresult = getUserListDeferred.await()
                _status.value = RestApiStatus.DONE
                _originalUserList.value = listresult

            }catch (e:Exception)
            {
                _status.value = RestApiStatus.ERROR
                _originalUserList.value = ArrayList()
            }
            _getUserList.postValue(_originalUserList.value)
        }
    }



//    private fun downloadUserList() {
//        uiScope.launch {
//            _originalUserList.value = GithubAPI.retrofitService.getUsersList().await()
//            _getUserList.postValue(_originalUserList.value)
//        }
//    }



//    private suspend fun downloadUserListFromServer(): List<GithubUser>? {
//        return withContext(Dispatchers.IO){
//            val resultList = GithubAPI.retrofitService.getUsersList().await()
//            resultList
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
