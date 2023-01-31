package com.example.rickandmorty_xml.domain.usecase

data class UseCases(
    val getAllCharactersUseCase: GetAllCharactersUseCase,
    val getSingleCharacterUseCase: GetSingleCharacterUseCase,
    val getCharacterLocationUseCase: GetCharacterLocationUseCase,
    val getMultipleCharactersUseCase: GetMultipleCharactersUseCase
)
