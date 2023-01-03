package com.example.rickandmorty_xml.data.remote.dto.getAllCharacters

import com.example.rickandmorty_xml.domain.model.Character
import com.example.rickandmorty_xml.domain.model.CharacterList

data class CharactersListDTO(
    val info: Info,
    val results: List<Result>
){

    fun toCharacterList(): CharacterList {
        val characterList = arrayListOf<Character>()
        results.forEach {
            characterList.add(Character(it.id, it.name, it.image))
        }
        return CharacterList(characterList)
    }
}