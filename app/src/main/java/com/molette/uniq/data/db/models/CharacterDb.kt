package com.molette.uniq.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.molette.uniq.presentation.models.Character

@Entity(tableName = "characters")
data class CharacterDb(
    @PrimaryKey
    val id: Long,
    val name: String,
    val thumbnail: String,
    val extension: String
) {
}

fun CharacterDb.toCharacter(): Character{
    return Character(id, name, thumbnail, extension)
}