package com.example.githubsearcher.model

import com.google.gson.annotations.SerializedName


class UserDetails(val name: String,
                  val email: String,
                  val location: String,
                  val followers: Int,
                  val following: Int,
                  val join_date: String,
                  val bio: String,
                  @SerializedName("avatar_url") val imgSrcUrl: String)