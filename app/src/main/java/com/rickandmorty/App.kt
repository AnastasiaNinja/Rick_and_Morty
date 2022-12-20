package com.rickandmorty

import android.app.Application
import com.rickandmorty.model.character.CharactersService.CharactersService
import com.rickandmorty.model.episodes.EpisodeService
import com.rickandmorty.model.locations.LocationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    val charactersService = CharactersService()
    val episodeService = EpisodeService()
    val locationService = LocationService()
}