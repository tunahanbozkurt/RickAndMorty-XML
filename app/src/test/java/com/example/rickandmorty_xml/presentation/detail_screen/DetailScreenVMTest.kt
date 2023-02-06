package com.example.rickandmorty_xml.presentation.detail_screen

import com.example.rickandmorty_xml.data.remote.dataSource.FakeRemoteDataSource
import com.example.rickandmorty_xml.data.remote.repository.FakeCharactersRepository
import com.example.rickandmorty_xml.domain.usecase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailScreenVMTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dataSource: FakeRemoteDataSource
    private lateinit var useCases: UseCases
    private lateinit var repository: FakeCharactersRepository
    private lateinit var viewModel: DetailScreenVM

    @Before
    fun setup() {
        dataSource = FakeRemoteDataSource()
        repository = FakeCharactersRepository(dataSource)
        useCases = UseCases(
            GetAllCharactersUseCase(repository),
            GetSingleCharacterUseCase(repository),
            GetCharacterLocationUseCase(repository),
            GetMultipleCharactersUseCase(repository)
        )
        viewModel = DetailScreenVM(useCases)
    }

    @Test
    fun `passes if detailScreenState's model != null, isLoading = false, hasError = false`() = runTest {
        viewModel.loadDetailState(0)
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.detailScreenState.collect()
        }
        val value = viewModel.detailScreenState.value

        assertThat(value.model).isNotNull()
        assertThat(value.isLoading).isFalse()
        assertThat(value.hasError).isFalse()

        collectJob.cancel()
    }

    @Test
    fun `passes if detailScreenState's model == null, isLoading = false, hasError = true`() = runTest {
        dataSource.setShouldReturnError()
        viewModel.loadDetailState(0)
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.detailScreenState.collect()
        }
        val value = viewModel.detailScreenState.value

        assertThat(value.model).isNull()
        assertThat(value.isLoading).isFalse()
        assertThat(value.hasError).isTrue()

        collectJob.cancel()
    }

    @Test
    fun `passes if locationListState is not empty`() = runTest {
        viewModel.loadDetailState(0)
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.locationListState.collect()
        }
        val value = viewModel.locationListState.value
        assertThat(value).isNotEmpty()

        collectJob.cancel()
    }
}