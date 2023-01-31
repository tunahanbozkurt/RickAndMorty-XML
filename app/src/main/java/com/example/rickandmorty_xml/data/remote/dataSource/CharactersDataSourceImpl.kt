package com.example.rickandmorty_xml.data.remote.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.AllCharactersResult
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterDTO
import com.example.rickandmorty_xml.data.remote.repository.AllCharactersPaging
import com.example.rickandmorty_xml.domain.dataSource.CharactersDataSource
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class CharactersDataSourceImpl(
    private val api: RickAndMortyAPI,
    private val dispatcherIO: CoroutineDispatcher
) : CharactersDataSource {

    override fun getAllCharacters(pageSize: Int): Flow<PagingData<AllCharactersResult>> {
        return Pager(
            PagingConfig(pageSize = pageSize)
        ) {
            AllCharactersPaging(api)
        }.flow
    }

    override fun loadCharacterById(id: Int): Flow<Resource<SingleCharacterDTO>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getSingleCharacter(id)
                if (response.isSuccessful) {
                    val model = response.body()!!
                    emit(Resource.Success(model))
                } else {
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(dispatcherIO)
    }
}