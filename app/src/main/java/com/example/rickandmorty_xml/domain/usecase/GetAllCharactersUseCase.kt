package com.example.rickandmorty_xml.domain.usecase

import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.repository.CharactersRepositoryImpl
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(
    private val repository: CharactersRepositoryImpl
) {

    operator fun invoke(pageSize: Int): Flow<PagingData<CharacterCardModel>> {
        return repository.getAllCharacters(pageSize)
    }
}