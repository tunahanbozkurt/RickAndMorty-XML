package com.example.rickandmorty_xml.domain.usecase

import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.CharacterDTO
import com.example.rickandmorty_xml.domain.repository.SingleCharacterRepository
import retrofit2.Response

class GetSingleCharacterUseCase(
    private val repository: SingleCharacterRepository
) {

    suspend operator fun invoke(id: Int): Response<CharacterDTO> {
        return repository.getSingleCharacter(id)
    }
}