package com.rickandmorty.model.character.room.entities

import com.rickandmorty.model.character.CharactersRepository
import com.rickandmorty.model.character.room.CharactersDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCharactersRepository(
    private val charactersDao: CharactersDao,
) : CharactersRepository {

    private fun getCharacterByName(characterId: Long): Flow<com.rickandmorty.model.character.entities.Character?> {
        return charactersDao.findByName(characterId).map { characterDbEntity -> characterDbEntity?.toCharacter()}
    }

}
