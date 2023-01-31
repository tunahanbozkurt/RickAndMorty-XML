package com.example.rickandmorty_xml.presentation.detail_screen

import androidx.lifecycle.viewModelScope
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.usecase.UseCases
import com.example.rickandmorty_xml.presentation.base.BaseViewModel
import com.example.rickandmorty_xml.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenVM @Inject constructor(
    private val useCases: UseCases
) : BaseViewModel() {


    private val _detailScreenState: MutableStateFlow<DetailScreenUIStates> = MutableStateFlow(
        DetailScreenUIStates()
    )
    val detailScreenState: StateFlow<DetailScreenUIStates> = _detailScreenState

    private val _locationList: MutableStateFlow<List<CharacterCardModel>> = MutableStateFlow(
        emptyList()
    )
    val locationList: StateFlow<List<CharacterCardModel>> = _locationList


    fun loadDetailState(id: Int) {
        viewModelScope.launch {
            useCases.getSingleCharacterUseCase(id).collect { result ->

                result.onError {
                    _detailScreenState.update { detailScreenUIStates ->
                        detailScreenUIStates.copy(hasError = true, isLoading = false)
                    }
                }

                result.onLoading {
                    _detailScreenState.update { detailScreenUIStates ->
                        detailScreenUIStates.copy(isLoading = true)
                    }
                }

                result.onSuccess {
                    loadDetailsForRecyclerview(
                        it.data.locationUrl.getLastPartOfUrl().toInt()
                    )
                    _detailScreenState.update { detailScreenUIStates ->
                        detailScreenUIStates.copy(
                            model = it.data,
                            isLoading = false,
                            hasError = false
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadDetailsForRecyclerview(locationId: Int) {
        useCases.getCharacterLocationUseCase(locationId).collectLatest { resource ->
            resource.onSuccess {
                useCases.getMultipleCharactersUseCase(
                    it.data.residents.getLastPartsOfUrls().map { it.toInt() })
                    .collectLatest { resource2 ->
                        if (resource2 is Resource.Success) {
                            _locationList.update { resource2.data }
                        }
                    }
            }
        }
    }
}
