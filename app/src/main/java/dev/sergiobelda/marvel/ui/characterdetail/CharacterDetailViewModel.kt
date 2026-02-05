/*
 * Copyright 2022 Sergio Belda Galbis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.marvel.ui.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sergiobelda.marvel.domain.doIfError
import dev.sergiobelda.marvel.domain.doIfSuccess
import dev.sergiobelda.marvel.domain.model.Character
import dev.sergiobelda.marvel.domain.usecase.GetCharacterDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    ) : ViewModel() {
        private val id: Int = savedStateHandle.get("id") ?: 0

        private val _characterUiState: MutableStateFlow<CharacterUiState> =
            MutableStateFlow(
                CharacterUiState(isLoading = true),
            )
        val characterUiState: StateFlow<CharacterUiState> get() = _characterUiState

        init {
            viewModelScope.launch {
                getCharacterDetailUseCase(id).collect { result ->
                    result.doIfSuccess { character ->
                        _characterUiState.update { it.copy(isLoading = false, character = character) }
                    }.doIfError { error ->
                        _characterUiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = error.message,
                            )
                        }
                    }
                }
            }
        }
    }

data class CharacterUiState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val errorMessage: String? = null,
)
