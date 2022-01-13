package dev.sergiobelda.marvel.usecase

import dev.sergiobelda.marvel.model.CharacterPreview
import dev.sergiobelda.marvel.repository.CharacterRepository

class GetCharactersUseCase(private val characterRepository: CharacterRepository) {

    operator fun invoke(): List<CharacterPreview> {
        return emptyList()
    }
}
