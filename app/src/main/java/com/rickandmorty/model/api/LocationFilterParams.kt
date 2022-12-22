package com.rickandmorty.model.api

data class LocationFilterParams(
    val type: String?,
    val dimension: String?,
) {
    var name: String? = null
}
