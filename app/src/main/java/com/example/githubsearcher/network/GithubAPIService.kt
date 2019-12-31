package com.example.githubsearcher.network

import com.example.githubsearcher.model.GithubUser
import com.example.githubsearcher.model.RepositoryData
import com.example.githubsearcher.model.UserDetails
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPIService {

    @GET("users")
    fun getUsersList(): Deferred<List<GithubUser>>



    @GET("users/{user}/")
    fun getUserDetails(@Path("user") user: String): Deferred<UserDetails>



    @GET("users/{user}/repos")
    fun getRepositories(): Deferred<List<RepositoryData>>

}