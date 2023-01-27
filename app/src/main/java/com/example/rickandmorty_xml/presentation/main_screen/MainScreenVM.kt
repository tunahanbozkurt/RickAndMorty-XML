package com.example.rickandmorty_xml.presentation.main_screen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmorty_xml.domain.usecase.UseCases
import com.example.rickandmorty_xml.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val PAGE_SIZE = 20

@HiltViewModel
class MainScreenVM @Inject constructor(
    useCases: UseCases
) : BaseViewModel() {

    val pagedItems = useCases.getAllCharactersUseCase(PAGE_SIZE).cachedIn(viewModelScope)
}