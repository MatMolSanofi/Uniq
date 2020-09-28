package com.molette.uniq.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_remote_keys")
class CharacterRemoteKeyDb(
    @PrimaryKey
    val characterId: Long,
    val previousKey: Int?,
    val nextKey: Int?
) {
}