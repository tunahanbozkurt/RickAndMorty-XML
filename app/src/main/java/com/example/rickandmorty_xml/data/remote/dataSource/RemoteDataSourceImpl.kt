package com.example.rickandmorty_xml.data.remote.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.AllCharactersResult
import com.example.rickandmorty_xml.data.remote.dto.getMultipleCharacter.MultipleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleLocation.CharacterLocationDTO
import com.example.rickandmorty_xml.data.remote.repository.AllCharactersPaging
import com.example.rickandmorty_xml.domain.dataSource.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class RemoteDataSourceImpl(
    private val api: RickAndMortyAPI,
    private val dispatcherIO: CoroutineDispatcher
) : RemoteDataSource {

    override fun getAllCharacters(pageSize: Int): Flow<PagingData<AllCharactersResult>> {
        return Pager(
            PagingConfig(pageSize = pageSize)
        ) {
            AllCharactersPaging(api)
        }.flow
    }

    override suspend fun getCharacterById(id: Int): Response<SingleCharacterDTO> {
        return withContext(dispatcherIO) {
            api.getSingleCharacter(id)
        }
    }

    override suspend fun getCharacterLocationById(id: Int): Response<CharacterLocationDTO> {
        return withContext(dispatcherIO) {
            api.getSingleLocation(id)
        }
    }

    override suspend fun getMultipleCharacters(idList: List<Int>): Response<MultipleCharacterDTO> {
        return withContext(dispatcherIO) {
            api.getMultipleCharacters(idList)
        }
    }
}