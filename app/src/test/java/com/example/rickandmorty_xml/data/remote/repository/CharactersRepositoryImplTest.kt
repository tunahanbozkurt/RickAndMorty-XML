package com.example.rickandmorty_xml.data.remote.repository

import com.example.rickandmorty_xml.data.remote.dataSource.FakeRemoteDataSource
import com.example.rickandmorty_xml.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersRepositoryImplTest {

    private lateinit var dataSource: FakeRemoteDataSource
    private lateinit var repository: FakeCharactersRepository

    @Before
    fun setup() {
        dataSource = FakeRemoteDataSource()
        repository = FakeCharactersRepository(dataSource)
    }

    @Test
    fun `getCharacterById() first emit is loading state and second is successful resource`() =
        runTest {
            val test = repository.getCharacterById(0).toList()
            val loadingCondition = test.first()
            val successfulResource = test.last()

            assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            assertThat(successfulResource).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getCharacterById() first emit is loading state and second is error resource`() =
        runTest {
            dataSource.setShouldReturnError()
            val test = repository.getCharacterById(0).toList()
            val loadingCondition = test.first()
            val failureResource = test.last()

            assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            assertThat(failureResource).isInstanceOf(Resource.Error::class.java)
        }

    @Test
    fun `getCharacterLocation() first emit is loading state and second is successful resource`() =
        runTest {
            val test = repository.getCharacterLocation(0).toList()
            val loadingCondition = test.first()
            val successfulResource = test.last()

            assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            assertThat(successfulResource).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getCharacterLocation() first emit is loading state and second is error resource`() =
        runTest {
            dataSource.setShouldReturnError()
            val test = repository.getCharacterLocation(0).toList()
            val loadingCondition = test.first()
            val failureResource = test.last()

            assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            assertThat(failureResource).isInstanceOf(Resource.Error::class.java)
        }

    @Test
    fun `getMultipleCharacters() first emit is loading state and second is successful resource`() =
        runTest {
            val test = repository.getMultipleCharacters(listOf()).toList()
            val loadingCondition = test.first()
            val successfulResource = test.last()
            assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            assertThat(successfulResource).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getMultipleCharacters() first emit is loading state and second is error resource`() =
        runTest {
            dataSource.setShouldReturnError()
            val test = repository.getMultipleCharacters(listOf()).toList()
            val loadingCondition = test.first()
            val failureResource = test.last()

            assertThat(loadingCondition).isInstanceOf(Resource.Loading::class.java)
            assertThat(failureResource).isInstanceOf(Resource.Error::class.java)
        }
}

