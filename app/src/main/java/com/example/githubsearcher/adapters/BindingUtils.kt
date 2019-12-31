package com.example.githubsearcher.adapters

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


@BindingAdapter("totalForks")
fun TextView.setTotalFork(item: RepositoryData) {
    text = item.forks.toString()+" forks"
}


@BindingAdapter("totalStars")
fun TextView.setTotalStars(item: RepositoryData) {
    text = item.stars.toString()+ " Stars"
}


//For User details in Repository screen

//@BindingAdapter("userName")
//fun TextView.setUserName(item: UserDetails) {
//    text = item.userId
//}
//
//@BindingAdapter("email")
//fun TextView.setEmail(item: UserDetails) {
//    text = item.email
//}
//
//@BindingAdapter("location")
//fun TextView.setLocation(item: UserDetails) {
//    text = item.location
//}
//
//@BindingAdapter("join_date")
//fun TextView.setJoinDate(item: UserDetails) {
//    text = item.join_date
//}
//
//@BindingAdapter("followers")
//fun TextView.setFollowers(item: UserDetails) {
//    text = item.followers.toString()
//}
//
//@BindingAdapter("following")
//fun TextView.setFollowing(item: UserDetails) {
//    text = item.following.toString()
//}
//
//@BindingAdapter("bio")
//fun TextView.setBio(item: UserDetails) {
//    text = item.bio
//}