package com.example.rickandmorty_xml.domain.usecase

import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.repository.CharactersRepository
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMultipleCharactersUseCase(
    private val repository: CharactersRepository
) {

    operator fun invoke(idList: List<Int>): Flow<Resource<List<CharacterCardModel>>> {
        return repository.getMultipleCharacters(idList)
    }
}