package com.example.rickandmorty_xml.presentation.main_screen

import com.example.rickandmorty_xml.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmorty_xml.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
   private val useCase: GetAllCharactersUseCase
): BaseViewModel() {
}