package dev.sergiobelda.marvel.ui.characterdetail

import androidx.lifecycle.ViewModel
import dev.sergiobelda.marvel.usecase.GetCharacterDetailUseCase

class CharacterDetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel()
