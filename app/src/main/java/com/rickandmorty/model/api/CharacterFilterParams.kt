package com.rickandmorty.model.api

data class CharacterFilterParams(
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?
) {
    var name: String? = null
}
