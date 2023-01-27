package com.example.rickandmorty_xml.domain.model

data class CharacterDetailModel(
    val id: Int,
    val characterName: String,
    val isAlive: String,
    val species: String,
    val imageUrl: String
)
