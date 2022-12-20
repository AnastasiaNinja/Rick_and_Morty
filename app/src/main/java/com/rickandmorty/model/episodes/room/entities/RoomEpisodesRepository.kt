package com.rickandmorty.model.episodes.room.entities

import com.rickandmorty.model.character.CharactersRepository
import com.rickandmorty.model.episodes.EpisodesRepository
import com.rickandmorty.model.episodes.entities.Episode
import com.rickandmorty.model.episodes.room.EpisodesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomEpisodesRepository(
    private val episodesDao: EpisodesDao,
) : EpisodesRepository {

    private fun getEpisodeByName(episodeId: Long): Flow<Episode?> {
        return episodesDao.findByName(episodeId)
            .map { episodeDbEntity -> episodeDbEntity?.toEpisode() }
    }

}