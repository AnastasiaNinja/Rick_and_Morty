package com.rickandmorty.model.episodes.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickandmorty.model.episodes.entities.Episode

@Entity(
    tableName = "episodes"
)
data class EpisodeDbEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val episode: String,
    val air_date: String
) {
    fun toEpisode(): Episode = Episode(
        id = id,
        name = name,
        episode = episode,
        air_date = air_date
    )
}