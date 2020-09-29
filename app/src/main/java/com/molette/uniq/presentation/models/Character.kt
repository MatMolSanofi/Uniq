package com.molette.uniq.presentation.models

data class Character(
    val id: Long,
    val characterId: Long,
    val name: String,
    val thumbnail: String,
    val extension: String
) {
}