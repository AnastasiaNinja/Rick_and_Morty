package com.rickandmorty.model.character.room

import androidx.room.Dao
import androidx.room.Query
import com.rickandmorty.model.character.room.entities.CharacterDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters WHERE id = :characterId")
    fun findByName(characterId: Long): Flow<CharacterDbEntity?>
}