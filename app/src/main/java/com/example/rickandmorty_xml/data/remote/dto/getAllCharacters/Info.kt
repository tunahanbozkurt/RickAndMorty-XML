package com.example.rickandmorty_xml.data.remote.dto.getAllCharacters

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)