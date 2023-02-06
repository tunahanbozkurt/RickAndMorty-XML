package com.example.rickandmorty_xml.domain.model

data class CharacterLocationModel(
    val id: Int,
    val name: String,
    val dimension: String,
    val type: String,
    val residents: List<String>
)
