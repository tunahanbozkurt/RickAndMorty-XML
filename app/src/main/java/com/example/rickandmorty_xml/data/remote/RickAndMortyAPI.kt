package com.example.rickandmorty_xml.data.remote

import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.CharactersListDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.CharacterDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("api/character")
    suspend fun getAllCharacters(
        @Query("page") page: Int? = null
    ): Response<CharactersListDTO>

    @GET("api/character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int? = 1
    ): Response<CharacterDTO>
}