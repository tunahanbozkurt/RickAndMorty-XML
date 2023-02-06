package com.example.rickandmorty_xml.domain.usecase

import com.example.rickandmorty_xml.data.remote.dataSource.FakeRemoteDataSource
import com.example.rickandmorty_xml.data.remote.repository.FakeCharactersRepository
import com.example.rickandmorty_xml.util.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMultipleCharactersUseCaseTest {

    private lateinit var dataSource: FakeRemoteDataSource
    private lateinit var repository: FakeCharactersRepository

    @Before
    fun setup() {
        dataSource = FakeRemoteDataSource()
        repository = FakeCharactersRepository(dataSource)
    }

    @Test
    fun `first emit is loading state and second is successful resource`() =
        runTest {
            val test = repository.getMultipleCharacters(listOf()).toList()
            val loadingCondition = test.first()
            val successfulResource = test.last()

            Truth.assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            Truth.assertThat(successfulResource).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `first emit is loading state and second is error resource`() =
        runTest {
            dataSource.setShouldReturnError()
            val test = repository.getMultipleCharacters(listOf()).toList()
            val loadingCondition = test.first()
            val failureResource = test.last()

            Truth.assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            Truth.assertThat(failureResource).isInstanceOf(Resource.Error::class.java)
        }
}