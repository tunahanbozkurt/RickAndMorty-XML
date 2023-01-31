package com.example.rickandmorty_xml.data.remote

import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.CharactersListDTO
import com.example.rickandmorty_xml.data.remote.dto.getMultipleCharacter.MultipleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.SingleCharacterDTO
import com.example.rickandmorty_xml.data.remote.dto.getSingleLocation.CharacterLocationDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("api/character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): Response<CharactersListDTO>

    @GET("api/character/{idList}")
    suspend fun getMultipleCharacters(
        @Path("idList") idList: List<Int>
    ): Response<MultipleCharacterDTO>

    @GET("api/character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<SingleCharacterDTO>

    @GET("api/location/{id}")
    suspend fun getSingleLocation(
        @Path("id") id: Int
    ): Response<CharacterLocationDTO>
}