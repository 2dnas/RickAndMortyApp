package com.example.rickandmorty

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide


fun View.visible(isVisible : Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun ImageView.setImage(url : String){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.rick_and_morty_episode_cover)
        .into((this))
}