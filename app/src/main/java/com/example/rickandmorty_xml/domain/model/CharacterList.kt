package com.example.rickandmorty_xml.domain.model


data class CharacterList(
    val list: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val image_url: String
)