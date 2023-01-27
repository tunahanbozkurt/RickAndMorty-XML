package com.example.rickandmorty_xml.domain.usecase

import androidx.paging.PagingData
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.repository.AllCharactersRepository
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(
    private val repository: AllCharactersRepository
) {

    operator fun invoke(pageSize: Int): Flow<PagingData<CharacterCardModel>> {
        return repository.getAllCharacters(pageSize)
    }
}