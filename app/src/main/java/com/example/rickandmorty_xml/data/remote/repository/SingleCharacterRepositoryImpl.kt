package com.example.rickandmorty_xml.data.remote.repository

import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.CharacterDTO
import com.example.rickandmorty_xml.di.Dispatchers
import com.example.rickandmorty_xml.domain.repository.SingleCharacterRepository
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class SingleCharacterRepositoryImpl(
    private val api: RickAndMortyAPI,
    @Dispatchers.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : SingleCharacterRepository {

    override fun loadCharacterById(id: Int): Flow<Resource<CharacterDTO>> = flow {
        emit(Resource.Loading())
        val response = api.getSingleCharacter(id)
        if (response.isSuccessful) {
            val nonNullableBody: CharacterDTO = response.body()!!
            emit(Resource.Success(nonNullableBody))
        } else {
            throw HttpException(response)
        }
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }.flowOn(dispatcherIO)
}