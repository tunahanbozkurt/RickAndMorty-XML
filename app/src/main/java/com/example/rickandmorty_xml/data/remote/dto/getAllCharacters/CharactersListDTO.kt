package com.example.rickandmorty_xml.data.remote.dto.getAllCharacters


data class CharactersListDTO(
    val info: AllCharactersInfo,
    val results: List<AllCharactersResult>
)