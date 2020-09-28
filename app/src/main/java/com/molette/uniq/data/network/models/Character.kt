package com.molette.uniq.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Character(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail
) {

}