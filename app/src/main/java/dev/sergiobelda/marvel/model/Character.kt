package dev.sergiobelda.marvel.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
    // TODO: List of comics.
)
