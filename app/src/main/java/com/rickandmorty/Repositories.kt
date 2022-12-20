package com.rickandmorty

import android.content.Context
import androidx.room.Room
import com.rickandmorty.model.character.CharactersRepository
import com.rickandmorty.model.character.room.entities.RoomCharactersRepository
import com.rickandmorty.model.episodes.EpisodesRepository
import com.rickandmorty.model.episodes.room.entities.RoomEpisodesRepository
import com.rickandmorty.model.locations.LocationsRepository
import com.rickandmorty.model.locations.room.entities.RoomLocationsRepository
import com.rickandmorty.model.room.AppDatabase

object Repositories {
    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .build()

    }

    val charactersRepository: CharactersRepository by lazy {
        RoomCharactersRepository(database.getCharactersDao())
    }
    val episodesRepository: EpisodesRepository by lazy {
        RoomEpisodesRepository(database.getEpisodesDao())
    }
    val locationsRepository: LocationsRepository by lazy {
        RoomLocationsRepository(database.getLocationsDao())
    }
}