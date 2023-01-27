package com.example.rickandmorty_xml.presentation.detail_screen

import androidx.lifecycle.viewModelScope
import com.example.rickandmorty_xml.data.remote.dto.getSingleCharacter.CharacterDTO
import com.example.rickandmorty_xml.domain.usecase.UseCases
import com.example.rickandmorty_xml.presentation.base.BaseViewModel
import com.example.rickandmorty_xml.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenVM @Inject constructor(
    private val useCases: UseCases
) : BaseViewModel() {


    private val _detailScreenState: MutableStateFlow<CharacterDTO?> = MutableStateFlow(null)
    val detailScreenState: StateFlow<CharacterDTO?> = _detailScreenState

    fun loadDetailState(id: Int) {
        viewModelScope.launch {
            useCases.getSingleCharacterUseCase(id).collect { result ->
                when (result) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _detailScreenState.update { result.data }
                    }
                }
            }
        }
    }
}