package com.rickandmorty.model.locations.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickandmorty.model.locations.entities.Location

@Entity(
    tableName = "locations"
)
data class LocationDbEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val type: String,
    val dimension: String
) {
    fun toLocation(): Location = Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}