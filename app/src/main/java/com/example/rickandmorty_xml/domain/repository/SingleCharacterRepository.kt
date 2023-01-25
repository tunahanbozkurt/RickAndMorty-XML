package com.example.rickandmorty_xml.domain.repository

import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.CharacterDTO
import retrofit2.Response

interface SingleCharacterRepository {

    suspend fun getSingleCharacter(id: Int): Response<CharacterDTO>
}