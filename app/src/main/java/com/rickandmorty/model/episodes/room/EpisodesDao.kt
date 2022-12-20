package com.rickandmorty.model.episodes.room

import androidx.room.Dao
import androidx.room.Query
import com.rickandmorty.model.episodes.room.entities.EpisodeDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episodes WHERE id = :episodeId")
    fun findByName(episodeId: Long): Flow<EpisodeDbEntity?>
}