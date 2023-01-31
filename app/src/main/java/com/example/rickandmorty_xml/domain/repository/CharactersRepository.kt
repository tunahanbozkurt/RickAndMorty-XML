package com.example.rickandmorty_xml.domain.repository

import androidx.paging.PagingData
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.model.CharacterDetailModel
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getAllCharacters(pageSize: Int): Flow<PagingData<CharacterCardModel>>
    fun loadCharacterById(id: Int): Flow<Resource<CharacterDetailModel>>
}