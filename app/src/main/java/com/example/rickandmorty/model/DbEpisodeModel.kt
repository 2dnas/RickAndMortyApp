package com.example.rickandmorty.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DbEpisodeModel(
    val id: Int? = 0,
    val name: String?,
    val airDate: String?,
    val episode: String?,
    val characters: List<String>?,
)