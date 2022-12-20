package com.rickandmorty.model.episodes

import com.rickandmorty.model.episodes.entities.Episode


typealias EpisodesListener = (episodes: List<Episode>) -> Unit

class EpisodeService {

    private var episodes: MutableList<Episode> = mutableListOf()

    private val listeners: MutableSet<EpisodesListener> = mutableSetOf()

    fun getEpisodes() : List<Episode> {
        return episodes
    }

    fun addListener(listener: EpisodesListener) {
        listeners.add(listener)
        listener.invoke(episodes)
    }

    fun removeListener(listener: EpisodesListener) {
        listeners.remove(listener)
    }


}