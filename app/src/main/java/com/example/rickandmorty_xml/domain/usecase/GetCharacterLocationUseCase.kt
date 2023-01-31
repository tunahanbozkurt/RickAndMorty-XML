package com.example.rickandmorty_xml.domain.usecase

import com.example.rickandmorty_xml.domain.model.CharacterLocation
import com.example.rickandmorty_xml.domain.repository.CharactersRepository
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCharacterLocationUseCase(
    private val repository: CharactersRepository
) {

    operator fun invoke(id: Int): Flow<Resource<CharacterLocation>> {
        return repository.getCharacterLocation(id)
    }
}