package com.example.rickandmorty_xml.di

import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.repository.AllCharactersRepositoryImpl
import com.example.rickandmorty_xml.data.remote.repository.PagingSource
import com.example.rickandmorty_xml.domain.repository.AllCharactersRepository
import com.example.rickandmorty_xml.domain.usecase.GetAllCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideAllCharactersRepository(pagingSource: PagingSource): AllCharactersRepository {
        return AllCharactersRepositoryImpl(pagingSource = pagingSource)
    }

    @Provides
    @Singleton
    fun providePagingSource(api: RickAndMortyAPI): PagingSource {
        return PagingSource(api)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: AllCharactersRepository): GetAllCharactersUseCase {
        return GetAllCharactersUseCase(repository)
    }
}