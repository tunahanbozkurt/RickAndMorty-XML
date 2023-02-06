package com.example.rickandmorty_xml.data.remote.dto.getSingleLocation

import com.example.rickandmorty_xml.domain.model.CharacterLocationModel
import com.example.rickandmorty_xml.util.uppercaseFirst

data class CharacterLocationDTO(
    val id: Int,
    val name: String,
    val dimension: String,
    val created: String,
    val type: String,
    val url: String,
    val residents: List<String>
) {

    fun toCharacterLocation(): CharacterLocationModel {
        return CharacterLocationModel(
            id = id,
            name = name.uppercaseFirst(),
            dimension = dimension.uppercaseFirst(),
            type = type.uppercaseFirst(),
            residents = residents
        )
    }
}