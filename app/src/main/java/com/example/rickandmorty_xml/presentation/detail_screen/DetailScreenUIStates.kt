package com.example.rickandmorty_xml.presentation.detail_screen

import com.example.rickandmorty_xml.domain.model.CharacterDetailModel

data class DetailScreenUIStates(
    val model: CharacterDetailModel? = null,
    val isLoading: Boolean = true,
    val hasError: Boolean = false
)
