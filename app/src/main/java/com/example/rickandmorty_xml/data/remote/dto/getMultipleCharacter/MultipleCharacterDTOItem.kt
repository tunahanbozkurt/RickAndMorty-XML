package com.example.rickandmorty_xml.data.remote.dto.getMultipleCharacter

import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.util.uppercaseFirst

data class MultipleCharacterDTOItem(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: MultipleCharacterLocation,
    val name: String,
    val origin: MultipleCharacterOrigin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    fun toCharacterCardModel(): CharacterCardModel {
        return CharacterCardModel(
            id = id,
            characterName = name.uppercaseFirst(),
            imageUrl = image,
            isAlive = status.uppercaseFirst(),
            origin = origin.name.uppercaseFirst(),
            species = species.uppercaseFirst(),
            lastKnownLocation = location.name.uppercaseFirst()
        )
    }
}