package com.example.githubsearcher.model

import com.google.gson.annotations.SerializedName

data class RepositoryData(val name:String,
                          @SerializedName("stargazers_count")val stars: Int,
                          val forks: Int,
                          @SerializedName("html_url")val url: String)