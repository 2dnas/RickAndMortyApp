package com.example.rickandmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.Episode

@Database(entities = [Episode::class, Character::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun getEpisodeDao() : EpisodeDao
    abstract fun getCharacterDao() : CharacterDao
}