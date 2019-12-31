package com.example.githubsearcher.model

import com.squareup.moshi.Json

data class RepositoryData(val name:String,
                          @Json(name = "stargazers_count")val stars: Int,
                          val forks: Int)