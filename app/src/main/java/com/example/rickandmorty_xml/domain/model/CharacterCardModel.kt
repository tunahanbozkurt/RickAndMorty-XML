package com.example.rickandmorty_xml.domain.model

data class CharacterCardModel(
    val id: Int,
    val characterName: String,
    val characterImageUrl: String,
    val isAlive: String,
    val origin: String,
    val species: String,
    val lastKnownLocation: String
)
