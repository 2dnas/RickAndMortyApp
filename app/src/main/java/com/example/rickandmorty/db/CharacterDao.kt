package com.example.rickandmorty.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.model.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CHARACTERS")
    fun getAllCharacter() : List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: Character)

    @Query("SElECT * FROM CHARACTERS WHERE id=:id")
    fun getCharacterById(id : Int) : Character?

    @Query("SELECT * FROM CHARACTERS WHERE name LIKE :name")
    fun getCharacterByName(name : String) : Character?
}