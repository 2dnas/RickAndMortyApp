package com.example.rickandmorty.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class EpisodesModel(
    val info: InfoHelper,
    val results : List<Episode>
)

data class InfoHelper(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?
)

@Entity(tableName = "EPISODES")
data class Episode(
    @PrimaryKey()
    val id: Int? = 0,
    val name: String?,
    @SerializedName("air_date")
    val airDate: String?,
    val episode: String?,
    val characters: List<String>?,
)