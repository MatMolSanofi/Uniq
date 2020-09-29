package com.molette.uniq.data.network.models

import com.molette.uniq.data.db.models.CharacterDb
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterRemote(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("thumbnail")
    val thumbnail: ThumbnailRemote
) {

}

fun CharacterRemote.toCharacterDb(): CharacterDb {
    return CharacterDb(id = 0, characterId = id, name = name, thumbnail = thumbnail.path, extension = thumbnail.extension)
}