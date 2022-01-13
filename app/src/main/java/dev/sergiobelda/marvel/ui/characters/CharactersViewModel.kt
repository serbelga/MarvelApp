package dev.sergiobelda.marvel.ui.characters

import androidx.lifecycle.ViewModel
import dev.sergiobelda.marvel.usecase.GetCharactersUseCase

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel()
