package com.example.rickandmorty_xml.presentation.detail_screen

import androidx.lifecycle.viewModelScope
import com.example.rickandmorty_xml.domain.usecase.UseCases
import com.example.rickandmorty_xml.presentation.base.BaseViewModel
import com.example.rickandmorty_xml.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun loadDetailState(id: Int) {
        viewModelScope.launch {
            useCases.getSingleCharacterUseCase(id).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _detailScreenState.update { detailScreenUIStates ->
                            detailScreenUIStates.copy(hasError = true, isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _detailScreenState.update { detailScreenUIStates ->
                            detailScreenUIStates.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        delay(1000)
                        _detailScreenState.update { detailScreenUIStates ->
                            detailScreenUIStates.copy(
                                model = result.data,
                                isLoading = false,
                                hasError = false
                            )
                        }
                    }
                }
            }
        }
    }
}