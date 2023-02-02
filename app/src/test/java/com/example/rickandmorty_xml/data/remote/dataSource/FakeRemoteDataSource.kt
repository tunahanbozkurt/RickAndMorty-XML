package com.example.rickandmorty_xml.data.remote.dataSource

import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.AllCharactersResult
import com.example.rickandmorty_xml.data.remote.dto.getMultipleCharacter.MultipleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleLocation.CharacterLocationDTO
import com.example.rickandmorty_xml.domain.dataSource.RemoteDataSource
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class FakeRemoteDataSource : RemoteDataSource {

    private val errorCode = 404

    private var shouldReturnError = false
    fun setShouldReturnError() {
        shouldReturnError = true
    }

    private var shouldThrowHttpException = false
    fun setShouldThrowHttpException() {
        shouldThrowHttpException = true
    }

    private var shouldThrowIOException = false
    fun setShouldThrowIOException() {
        shouldThrowIOException = true
    }

    override fun getAllCharacters(pageSize: Int): Flow<PagingData<AllCharactersResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterById(id: Int): Response<SingleCharacterDTO> {
        val response = mockk<Response<CharacterLocationDTO>>(relaxed = true)
        val model = mockk<SingleCharacterDTO>()

        if (shouldThrowHttpException) {
            throw HttpException(response)
        }

        if (shouldThrowIOException) {
            throw IOException()
        }

        if (shouldReturnError) {
            return Response.error(errorCode, response.errorBody()!!)
        }
        return Response.success(model)
    }

    override suspend fun getCharacterLocationById(id: Int): Response<CharacterLocationDTO> {
        val response = mockk<Response<CharacterLocationDTO>>(relaxed = true)
        val model = mockk<CharacterLocationDTO>()

        if (shouldThrowHttpException) {
            throw HttpException(response)
        }

        if (shouldThrowIOException) {
            throw IOException()
        }

        if (shouldReturnError) {
            return Response.error(errorCode, response.errorBody()!!)
        }
        return Response.success(model)
    }

    override suspend fun getMultipleCharacters(idList: List<Int>): Response<MultipleCharacterDTO> {
        val response = mockk<Response<MultipleCharacterDTO>>(relaxed = true)
        val model = MultipleCharacterDTO()

        if (shouldThrowHttpException) {
            throw HttpException(response)
        }

        if (shouldThrowIOException) {
            throw IOException()
        }

        if (shouldReturnError) {
            return Response.error(errorCode, response.errorBody()!!)
        }
        return Response.success(model)
    }
}
