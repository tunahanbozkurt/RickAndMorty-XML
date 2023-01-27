package com.example.rickandmorty_xml.data.remote.dto.getAllCharacters

import com.example.rickandmorty_xml.domain.model.CharacterCardModel

data class AllCharactersResult(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: AllCharactersLocation,
    val name: String,
    val origin: AllCharactersOrigin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {

    fun toCharacterCardModel(): CharacterCardModel {
        return CharacterCardModel(
            id = id,
            characterName = name.replaceFirstChar { it.uppercase() },
            characterImageUrl = image,
            isAlive = status.replaceFirstChar { it.uppercase() },
            origin = origin.name.replaceFirstChar { it.uppercase() },
            species = species.replaceFirstChar { it.uppercase() },
            lastKnownLocation = location.name.replaceFirstChar { it.uppercase() }
        )
    }
}