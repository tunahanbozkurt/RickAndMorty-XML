package com.example.rickandmorty_xml.data.remote.repository

import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.CharacterDTO
import com.example.rickandmorty_xml.di.Dispatchers
import com.example.rickandmorty_xml.domain.repository.SingleCharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class SingleCharacterRepositoryImpl(
    private val api: RickAndMortyAPI,
    @Dispatchers.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : SingleCharacterRepository {

    override suspend fun getSingleCharacter(id: Int): Response<CharacterDTO> {
        return withContext(dispatcherIO) {
            api.getSingleCharacter(id)
        }
    }
}