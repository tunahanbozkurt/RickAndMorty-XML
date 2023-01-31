package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty_xml.domain.dataSource.RemoteDataSource
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.model.CharacterDetailModel
import com.example.rickandmorty_xml.domain.model.CharacterLocation
import com.example.rickandmorty_xml.domain.repository.CharactersRepository
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class CharactersRepositoryImpl(
    private val dataSource: RemoteDataSource
) : CharactersRepository {

    override fun getAllCharacters(pageSize: Int): Flow<PagingData<CharacterCardModel>> {
        return dataSource.getAllCharacters(pageSize).map { pagingData ->
            pagingData.map {
                it.toCharacterCardModel()
            }
        }
    }

    override fun getCharacterById(id: Int): Flow<Resource<CharacterDetailModel>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = dataSource.getCharacterById(id)
                if (response.isSuccessful) {
                    val model = response.body()!!.toCharacterDetailModel()
                    emit(Resource.Success(model))
                } else {
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun getCharacterLocation(id: Int): Flow<Resource<CharacterLocation>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = dataSource.getCharacterLocationById(id)
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()!!.toCharacterLocation()))
                } else {
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun getMultipleCharacters(idList: List<Int>): Flow<Resource<List<CharacterCardModel>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = dataSource.getMultipleCharacter(idList)
                if (response.isSuccessful) {
                    val model = response.body()!!.map {
                        it.toCharacterCardModel()
                    }
                    emit(Resource.Success(model))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}