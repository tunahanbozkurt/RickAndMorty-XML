package com.example.rickandmorty_xml.domain.usecase

import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.CharacterDTO
import com.example.rickandmorty_xml.domain.repository.SingleCharacterRepository
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

class GetSingleCharacterUseCase(
    private val repository: SingleCharacterRepository
) {

    operator fun invoke(id: Int): Flow<Resource<CharacterDTO>> {
        return repository.loadCharacterById(id)
    }

}