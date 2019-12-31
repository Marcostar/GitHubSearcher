package com.example.githubsearcher.model

import com.google.gson.annotations.SerializedName

data class GithubUser(@SerializedName("login")val userId: String,
                      @SerializedName("avatar_url") val imgSrcUrl: String)