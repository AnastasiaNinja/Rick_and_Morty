package com.rickandmorty.model.locations.room

import androidx.room.Dao
import androidx.room.Query
import com.rickandmorty.model.locations.room.entities.LocationDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations WHERE id = :locationId")
    fun findByName(locationId: Long): Flow<LocationDbEntity?>
}