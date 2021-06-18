package com.example.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class CertainEpisodeModel (
    val id : Int?,
    val name: String?,
    @SerializedName("air_date")
    val airDate : String?,
    val episode : String?,
    val characters : List<String>
    )