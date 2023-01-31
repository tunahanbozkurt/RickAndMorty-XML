package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty_xml.domain.dataSource.CharactersDataSource
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.model.CharacterDetailModel
import com.example.rickandmorty_xml.domain.repository.CharactersRepository
import com.example.rickandmorty_xml.util.Resource
import com.example.rickandmorty_xml.util.convert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersRepositoryImpl(
    private val dataSource: CharactersDataSource
): CharactersRepository {
    override fun getAllCharacters(pageSize: Int): Flow<PagingData<CharacterCardModel>> {
        return dataSource.getAllCharacters(pageSize).map { pagingData ->
            pagingData.map {
                it.toCharacterCardModel()
            }
        }
    }

    override fun loadCharacterById(id: Int): Flow<Resource<CharacterDetailModel>> {
        return dataSource.loadCharacterById(id).map { resource ->
            resource.convert { dto ->
                dto.toCharacterDetailModel()
            }
        }
    }
}