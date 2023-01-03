package com.example.rickandmorty_xml.domain.repository

import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result
import kotlinx.coroutines.flow.Flow

interface AllCharactersRepository {

    fun getAllCharacters(): Flow<PagingData<Result>>
}