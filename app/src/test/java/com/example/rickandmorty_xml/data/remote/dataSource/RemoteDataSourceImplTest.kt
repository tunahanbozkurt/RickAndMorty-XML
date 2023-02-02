package com.example.rickandmorty_xml.data.remote.dataSource

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceImplTest {

    private lateinit var dataSource: FakeRemoteDataSource

    @Before
    fun setup() {
        dataSource = FakeRemoteDataSource()
    }

    @Test
    fun `getCharacterById() returns successful response if everything is fine`() = runTest {
        val test = dataSource.getCharacterById(0).isSuccessful
        assertThat(test).isTrue()
    }

    @Test
    fun `getCharacterById() returns unsuccessful response if something goes wrong`() = runTest {
        dataSource.setShouldReturnError()
        val test = dataSource.getCharacterById(0).isSuccessful
        assertThat(test).isFalse()
    }

    @Test
    fun `getCharacterLocationById() returns successful response if everything is fine`() = runTest {
        val test = dataSource.getCharacterLocationById(0).isSuccessful
        assertThat(test).isTrue()
    }

    @Test
    fun `getCharacterLocationById() returns unsuccessful response if something goes wrong`() = runTest {
        dataSource.setShouldReturnError()
        val test = dataSource.getCharacterLocationById(0).isSuccessful
        assertThat(test).isFalse()
    }

    @Test
    fun `getMultipleCharacters() returns successful response if everything is fine`() = runTest {
        val test = dataSource.getMultipleCharacters(listOf()).isSuccessful
        assertThat(test).isTrue()
    }

    @Test
    fun `getMultipleCharacters() returns unsuccessful response if something goes wrong`() = runTest {
        dataSource.setShouldReturnError()
        val test = dataSource.getMultipleCharacters(listOf()).isSuccessful
        assertThat(test).isFalse()
    }
}