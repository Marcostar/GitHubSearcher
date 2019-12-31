package com.example.githubsearcher.model

import com.squareup.moshi.Json

data class GithubUser(@Json(name = "login")val userId: String,
                      @Json(name = "avatar_url") val imgSrcUrl: String)