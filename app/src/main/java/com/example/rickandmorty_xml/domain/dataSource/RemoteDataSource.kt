package com.example.rickandmorty_xml.domain.dataSource

import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.AllCharactersResult
import com.example.rickandmorty_xml.data.remote.dto.getMultipleCharacter.MultipleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleLocation.CharacterLocationDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    fun getAllCharacters(pageSize: Int): Flow<PagingData<AllCharactersResult>>
    suspend fun getCharacterById(id: Int): Response<SingleCharacterDTO>
    suspend fun getCharacterLocationById(id: Int): Response<CharacterLocationDTO>
    suspend fun getMultipleCharacter(idList: List<Int>): Response<MultipleCharacterDTO>
}