package com.example.rickandmorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CharacterModel (
    val info : InfoHelper?,
    val results : List<Character>
        )


@Entity(tableName = "CHARACTERS")
data class Character(
    @PrimaryKey
    val id : Int? = 0,
    val name : String?,
    val status : String?,
    val species : String?,
    val type : String?,
    val gender : String?,
    val image : String?,
    val episode : List<String>?
)