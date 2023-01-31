package com.example.rickandmorty_xml.domain.dataSource

import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.AllCharactersResult
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterDTO
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharactersDataSource {
    fun getAllCharacters(pageSize: Int): Flow<PagingData<AllCharactersResult>>
    fun loadCharacterById(id: Int): Flow<Resource<SingleCharacterDTO>>
}