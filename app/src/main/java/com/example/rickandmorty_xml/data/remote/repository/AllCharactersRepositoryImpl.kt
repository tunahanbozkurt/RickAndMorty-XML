package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result
import com.example.rickandmorty_xml.domain.repository.AllCharactersRepository
import kotlinx.coroutines.flow.Flow

class AllCharactersRepositoryImpl(
    private val api: RickAndMortyAPI
) : AllCharactersRepository {

    override fun getAllCharacters(pageSize: Int): Flow<PagingData<Result>> {
        return Pager(
            PagingConfig(pageSize = pageSize)
        ) {
            AllCharactersPaging(api)
        }.flow
    }
}