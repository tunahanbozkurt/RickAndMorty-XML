package com.example.rickandmorty_xml.presentation.main_screen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty_xml.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmorty_xml.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result

const val PAGE_SIZE = 20

@HiltViewModel
class MainScreenVM @Inject constructor(
   private val useCase: GetAllCharactersUseCase
): BaseViewModel() {

   fun loadPagedData(): Flow<PagingData<Result>> {
      return useCase.invoke(PAGE_SIZE).cachedIn(viewModelScope)
   }
 }