package com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter

import com.example.rickandmorty_xml.domain.model.CharacterDetailModel

data class SingleCharacterDTO(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: SingleCharacterLocation,
    val name: String,
    val origin: SingleCharacterOrigin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {

    fun toCharacterDetailModel(): CharacterDetailModel {
        return CharacterDetailModel(
            id = id,
            characterName = name,
            isAlive = status,
            species = species,
            imageUrl = image
        )
    }
}