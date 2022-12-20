package com.rickandmorty.model.character.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickandmorty.model.character.entities.Character

@Entity(
    tableName = "characters"
)
data class CharacterDbEntity (
    @PrimaryKey val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
        ) {

    fun toCharacter(): Character = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image

    )

}