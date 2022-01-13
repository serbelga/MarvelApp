package dev.sergiobelda.marvel.usecase

import dev.sergiobelda.marvel.model.Character
import dev.sergiobelda.marvel.repository.CharacterRepository

class GetCharacterDetailUseCase(private val characterRepository: CharacterRepository) {

    operator fun invoke(): Character {
        TODO()
    }
}
