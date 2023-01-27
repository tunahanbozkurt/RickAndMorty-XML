package com.example.rickandmorty_xml.domain.repository

import androidx.paging.PagingData
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import kotlinx.coroutines.flow.Flow

interface AllCharactersRepository {

    fun getAllCharacters(pageSize: Int): Flow<PagingData<CharacterCardModel>>
}