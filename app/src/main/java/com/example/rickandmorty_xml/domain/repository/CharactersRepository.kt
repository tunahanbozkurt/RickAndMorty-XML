package com.example.rickandmorty_xml.domain.repository

import androidx.paging.PagingData
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.model.CharacterDetailModel
import com.example.rickandmorty_xml.domain.model.CharacterLocationModel
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getAllCharacters(pageSize: Int): Flow<PagingData<CharacterCardModel>>
    fun getCharacterById(id: Int): Flow<Resource<CharacterDetailModel>>
    fun getCharacterLocation(id: Int): Flow<Resource<CharacterLocationModel>>
    fun getMultipleCharacters(idList: List<Int>): Flow<Resource<List<CharacterCardModel>>>
}