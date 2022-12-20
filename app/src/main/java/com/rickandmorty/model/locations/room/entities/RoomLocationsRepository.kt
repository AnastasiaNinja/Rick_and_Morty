package com.rickandmorty.model.locations.room.entities

import com.rickandmorty.model.locations.LocationsRepository
import com.rickandmorty.model.locations.entities.Location
import com.rickandmorty.model.locations.room.LocationsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocationsRepository (
    private val locationsDao: LocationsDao,
    ) : LocationsRepository {

        private fun getLocationByName(locationId: Long): Flow<Location?> {
            return locationsDao.findByName(locationId)
                .map { locationDbEntity -> locationDbEntity?.toLocation() }
        }
}