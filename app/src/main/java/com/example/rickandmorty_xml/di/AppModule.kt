package com.example.rickandmorty_xml.di

import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.repository.AllCharactersRepositoryImpl
import com.example.rickandmorty_xml.data.remote.repository.SingleCharacterRepositoryImpl
import com.example.rickandmorty_xml.domain.repository.AllCharactersRepository
import com.example.rickandmorty_xml.domain.repository.SingleCharacterRepository
import com.example.rickandmorty_xml.domain.usecase.GetAllCharactersUseCase
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
    fun provideAllCharactersRepo(api: RickAndMortyAPI): AllCharactersRepository {
        return AllCharactersRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: AllCharactersRepository): GetAllCharactersUseCase {
        return GetAllCharactersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSingleCharacterRepo(
        api: RickAndMortyAPI,
        @Dispatchers.DispatcherIO dispatcherIO: CoroutineDispatcher
    ): SingleCharacterRepository {
        return SingleCharacterRepositoryImpl(api = api, dispatcherIO)
    }
}