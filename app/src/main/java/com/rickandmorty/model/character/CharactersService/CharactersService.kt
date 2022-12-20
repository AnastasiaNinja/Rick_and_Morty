package com.rickandmorty.model.character.CharactersService

typealias CharactersListener = (characters: List<com.rickandmorty.model.character.entities.Character>) -> Unit

class CharactersService {

    private var characters: MutableList<com.rickandmorty.model.character.entities.Character> = mutableListOf()

    private val listeners: MutableSet<CharactersListener> = mutableSetOf()

    fun getCharacters() : List<com.rickandmorty.model.character.entities.Character> {
        return characters
    }

    fun addListener(listener: CharactersListener) {
        listeners.add(listener)
        listener.invoke(characters)
    }

    fun removeListener(listener: CharactersListener) {
        listeners.remove(listener)
    }


}