package com.molette.uniq.data.db.models

import androidx.room.Entity

@Entity(tableName = "characters")
data class CharacterDb(
    val id: Long,
    val name: String,
    val thumbnail: String,
    val extension: String
) {
}