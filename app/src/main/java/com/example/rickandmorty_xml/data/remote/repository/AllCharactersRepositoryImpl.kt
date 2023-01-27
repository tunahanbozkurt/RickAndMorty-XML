package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.repository.AllCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AllCharactersRepositoryImpl(
    private val api: RickAndMortyAPI
) : AllCharactersRepository {

    override fun getAllCharacters(pageSize: Int): Flow<PagingData<CharacterCardModel>> {
        return Pager(
            PagingConfig(pageSize = pageSize)
        ) {
            AllCharactersPaging(api)
        }.flow
            .map { pagingData ->
                pagingData.map {
                    it.toCharacterCardModel()
                }
            }
    }
}