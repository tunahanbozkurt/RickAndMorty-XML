package com.example.rickandmorty_xml.domain.usecase

import com.example.rickandmorty_xml.domain.model.CharacterDetailModel
import com.example.rickandmorty_xml.domain.repository.CharactersRepository
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

class GetSingleCharacterUseCase(
    private val repository: CharactersRepository
) {

    operator fun invoke(id: Int): Flow<Resource<CharacterDetailModel>> {
        return repository.getCharacterById(id)
    }

}