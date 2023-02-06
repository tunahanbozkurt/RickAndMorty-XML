package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.dataSource.FakeRemoteDataSource
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.model.CharacterDetailModel
import com.example.rickandmorty_xml.domain.model.CharacterLocationModel
import com.example.rickandmorty_xml.domain.repository.CharactersRepository
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FakeCharactersRepository(
    private val dataSource: FakeRemoteDataSource
) : CharactersRepository {

    override fun getAllCharacters(pageSize: Int): Flow<PagingData<CharacterCardModel>> {
        TODO()
    }

    override fun getCharacterById(id: Int): Flow<Resource<CharacterDetailModel>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = dataSource.getCharacterById(id)
                if (response.isSuccessful) {
                    val model = response.body()?.toCharacterDetailModel()
                        ?: throw Exception("Response body is null")
                    emit(Resource.Success(model))
                } else {
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun getCharacterLocation(id: Int): Flow<Resource<CharacterLocationModel>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = dataSource.getCharacterLocationById(id)
                if (response.isSuccessful) {
                    val model = response.body()?.toCharacterLocation()
                        ?: throw Exception("Response body is null")
                    emit(Resource.Success(model))
                } else {
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun getMultipleCharacters(idList: List<Int>): Flow<Resource<List<CharacterCardModel>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = dataSource.getMultipleCharacters(idList)
                if (response.isSuccessful) {
                    val model = response.body()?.map {
                        it.toCharacterCardModel()
                    } ?: throw Exception("Response body is null")
                    emit(Resource.Success(model))
                } else {
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}
