package com.example.rickandmorty_xml.di

import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dataSource.RemoteDataSourceImpl
import com.example.rickandmorty_xml.data.remote.repository.CharactersRepositoryImpl
import com.example.rickandmorty_xml.domain.dataSource.RemoteDataSource
import com.example.rickandmorty_xml.domain.usecase.*
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
            getSingleCharacterUseCase = GetSingleCharacterUseCase(repository),
            getCharacterLocationUseCase = GetCharacterLocationUseCase(repository),
            getMultipleCharactersUseCase = GetMultipleCharactersUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCharactersDataSource(
        api: RickAndMortyAPI,
        @Dispatchers.DispatcherIO dispatcherIO: CoroutineDispatcher
    ): RemoteDataSource {
        return RemoteDataSourceImpl(api = api, dispatcherIO = dispatcherIO)
    }

    @Provides
    @Singleton
    fun provideCharactersRepository(dataSource: RemoteDataSource): CharactersRepositoryImpl {
        return CharactersRepositoryImpl(dataSource)
    }
}