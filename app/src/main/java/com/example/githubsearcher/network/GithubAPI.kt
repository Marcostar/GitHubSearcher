package com.example.githubsearcher.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubAPI {
    val retrofitService : GithubAPIService by lazy { retrofit.create(GithubAPIService::class.java) }
}


private const val BASE_URL = "https://api.github.com/"


//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()
