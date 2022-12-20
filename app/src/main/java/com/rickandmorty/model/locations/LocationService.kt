package com.rickandmorty.model.locations

import com.rickandmorty.model.locations.entities.Location


typealias LocationsListener = (locations: List<Location>) -> Unit

class LocationService {

    private var locations: MutableList<Location> = mutableListOf()

    private val listeners: MutableSet<LocationsListener> = mutableSetOf()

    fun getLocations() : List<Location> {
        return locations
    }

    fun addListener(listener: LocationsListener) {
        listeners.add(listener)
        listener.invoke(locations)
    }

    fun removeListener(listener: LocationsListener) {
        listeners.remove(listener)
    }


}