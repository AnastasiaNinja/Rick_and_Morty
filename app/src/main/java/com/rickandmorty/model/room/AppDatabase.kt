package com.rickandmorty.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rickandmorty.model.character.room.entities.CharacterDbEntity
import com.rickandmorty.model.character.room.CharactersDao
import com.rickandmorty.model.episodes.room.EpisodesDao
import com.rickandmorty.model.episodes.room.entities.EpisodeDbEntity
import com.rickandmorty.model.locations.room.LocationsDao
import com.rickandmorty.model.locations.room.entities.LocationDbEntity

@Database(
    version = 1,
    entities = [
        CharacterDbEntity::class,
        EpisodeDbEntity::class,
        LocationDbEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getCharactersDao(): CharactersDao
    abstract fun getEpisodesDao(): EpisodesDao
    abstract fun getLocationsDao(): LocationsDao
}