package com.example.rickandmorty_xml.data.remote.dto.getAllCharacters

data class AllCharactersInfo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)