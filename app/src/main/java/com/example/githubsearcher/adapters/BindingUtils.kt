package com.example.githubsearcher.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.githubsearcher.model.GithubUser
import com.example.githubsearcher.model.RepositoryData

@BindingAdapter("repositoryName")
fun TextView.setRepositoryName(item: RepositoryData) {
    text = item.name
}


@BindingAdapter("totalForks")
fun TextView.setTotalFork(item: RepositoryData) {
    text = item.forks.toString()
}


@BindingAdapter("totalStars")
fun TextView.setTotalStars(item: RepositoryData) {
    text = item.stars.toString()
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("userName")
fun TextView.setTotalStars(item: GithubUser) {
    text = item.userId
}