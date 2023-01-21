package com.example.rickandmorty_xml.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result
import com.example.rickandmorty_xml.domain.repository.AllCharactersRepository

class GetAllCharactersUseCase(
    private val repository: AllCharactersRepository
) {

    operator fun invoke(pageSize: Int): Flow<PagingData<Result>> {
        return repository.getAllCharacters(pageSize)
    }
}