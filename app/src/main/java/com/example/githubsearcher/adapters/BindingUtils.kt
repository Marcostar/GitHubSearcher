package com.example.githubsearcher.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubsearcher.R
import com.example.githubsearcher.model.GithubUser
import com.example.githubsearcher.model.RepositoryData
import com.example.githubsearcher.model.UserDetails
import com.example.githubsearcher.screens.repoScreen.RestApiStatus

//For Github Users screen

@BindingAdapter("imageUrl")
fun setImageUrl(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("userName")
fun TextView.setTotalStars(item: GithubUser) {
    text = item.userId
}

//For Repository list items

@BindingAdapter("repositoryName")
fun TextView.setRepositoryName(item: RepositoryData) {
    text = item.name
}


@SuppressLint("SetTextI18n")
@BindingAdapter("totalForks")
fun TextView.setTotalFork(item: RepositoryData) {
    text = item.forks.toString()+" Forks"
}


@SuppressLint("SetTextI18n")
@BindingAdapter("totalStars")
fun TextView.setTotalStars(item: RepositoryData) {
    text = item.stars.toString()+ " Stars"
}


@BindingAdapter("restApiStatus")
fun bindStatus(statusImageView: ImageView, status: RestApiStatus?) {
    when (status) {
        RestApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        RestApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        RestApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}