package com.example.rickandmorty_xml.domain.repository

import com.example.rickandmorty_xml.domain.model.CharacterDetailModel
import com.example.rickandmorty_xml.util.Resource
import kotlinx.coroutines.flow.Flow

interface SingleCharacterRepository {

    fun loadCharacterById(id: Int): Flow<Resource<CharacterDetailModel>>

}