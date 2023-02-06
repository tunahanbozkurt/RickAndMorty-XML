package com.example.rickandmorty_xml.data.remote.dataSource

import androidx.paging.PagingData
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.AllCharactersResult
import com.example.rickandmorty_xml.data.remote.dto.getMultipleCharacter.MultipleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getMultipleCharacter.MultipleCharacterDTOItem
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterLocation
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterOrigin
import com.example.rickandmorty_xml.data.remote.dto.getSingleLocation.CharacterLocationDTO
import com.example.rickandmorty_xml.domain.dataSource.RemoteDataSource
import io.mockk.every
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

    private var shouldResponseBodyReturnNull = false
    fun setShouldResponseBodyReturnNull() {
        shouldResponseBodyReturnNull = true
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
        val model = SingleCharacterDTO(
            created = "",
            episode = listOf(),
            gender = "",
            id = 0,
            image = "",
            location = SingleCharacterLocation("","https://rickandmortyapi.com/api/location/1"),
            name = "",
            origin = SingleCharacterOrigin("","https://rickandmortyapi.com/api/location/1"),
            species = "",
            status = "",
            type = "",
            url = "https://rickandmortyapi.com/api/location/1"
        )

        val successfulResponse = Response.success(model)
        val failureResponse = Response.error<SingleCharacterDTO>(errorCode, response.errorBody()!!)

        if (shouldResponseBodyReturnNull) {
            every { successfulResponse.body() } returns null
        }

        if (shouldThrowHttpException) {
            throw HttpException(response)
        }

        if (shouldThrowIOException) {
            throw IOException()
        }

        if (shouldReturnError) {
            return failureResponse
        }
        return successfulResponse
    }

    override suspend fun getCharacterLocationById(id: Int): Response<CharacterLocationDTO> {
        val response = mockk<Response<CharacterLocationDTO>>(relaxed = true)
        val model = mockk<CharacterLocationDTO>(relaxed = true)

        val successfulResponse = Response.success(model)
        val failureResponse = Response.error<CharacterLocationDTO>(errorCode, response.errorBody()!!)

        if (shouldResponseBodyReturnNull) {
            every { successfulResponse.body() } returns null
        }

        if (shouldThrowHttpException) {
            throw HttpException(response)
        }

        if (shouldThrowIOException) {
            throw IOException()
        }

        if (shouldReturnError) {
            return failureResponse
        }
        return successfulResponse
    }

    override suspend fun getMultipleCharacters(idList: List<Int>): Response<MultipleCharacterDTO> {
        val response = mockk<Response<MultipleCharacterDTO>>(relaxed = true)
        val modelList = MultipleCharacterDTO()
        val mockModel = mockk<MultipleCharacterDTOItem>(relaxed = true)
        modelList.add(mockModel)

        val successfulResponse = Response.success(modelList)
        val failureResponse = Response.error<MultipleCharacterDTO>(errorCode, response.errorBody()!!)

        if (shouldResponseBodyReturnNull) {
            every { successfulResponse.body() } returns null
        }

        if (shouldThrowHttpException) {
            throw HttpException(response)
        }

        if (shouldThrowIOException) {
            throw IOException()
        }

        if (shouldReturnError) {
            return failureResponse
        }
        return successfulResponse
    }
}

