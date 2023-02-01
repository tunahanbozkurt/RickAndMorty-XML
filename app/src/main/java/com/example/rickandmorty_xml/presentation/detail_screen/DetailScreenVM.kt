package com.example.rickandmorty_xml.presentation.detail_screen

import androidx.lifecycle.viewModelScope
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.domain.model.CharacterLocation
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

                result.onSuccess { resource ->
                    val characterIdFromUrl = resource.data.locationUrl.getLastPartOfUrl()?.toInt()
                    loadDetailsForRecyclerview(characterIdFromUrl)
                    _detailScreenState.update { detailScreenUIStates ->
                        detailScreenUIStates.copy(
                            model = resource.data,
                            isLoading = false,
                            hasError = false
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadDetailsForRecyclerview(locationId: Int?) {
        if (locationId != null) {
            useCases.getCharacterLocationUseCase(locationId).collectLatest { resource ->
                resource.onSuccess {
                    getMultipleCharacters(it.data)
                }
            }
        }
    }

    private suspend fun getMultipleCharacters(data: CharacterLocation) {
        val characterIds = data.residents.getLastPartsOfUrls().map { it.toInt() }
        useCases.getMultipleCharactersUseCase(characterIds)
            .collectLatest { resource ->
                resource.onSuccess { list ->
                    _locationList.update { list.data }
                }
            }
    }
}
