package com.example.rickandmorty_xml.di

import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dataSource.CharactersDataSourceImpl
import com.example.rickandmorty_xml.data.remote.repository.CharactersRepositoryImpl
import com.example.rickandmorty_xml.domain.dataSource.CharactersDataSource
import com.example.rickandmorty_xml.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmorty_xml.domain.usecase.GetSingleCharacterUseCase
import com.example.rickandmorty_xml.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickAndMortyAPI(): RickAndMortyAPI {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: CharactersRepositoryImpl): UseCases {
        return UseCases(
            getAllCharactersUseCase = GetAllCharactersUseCase(repository),
            getSingleCharacterUseCase = GetSingleCharacterUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCharactersDataSource(
        api: RickAndMortyAPI,
        @Dispatchers.DispatcherIO dispatcherIO: CoroutineDispatcher
    ): CharactersDataSource {
        return CharactersDataSourceImpl(api = api, dispatcherIO = dispatcherIO)
    }

    @Provides
    @Singleton
    fun provideCharactersRepository(dataSource: CharactersDataSource): CharactersRepositoryImpl {
        return CharactersRepositoryImpl(dataSource)
    }
}