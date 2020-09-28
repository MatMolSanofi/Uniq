package com.molette.uniq.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Thumbnail(
    @SerialName("path")
    val path: String,
    @SerialName("extension")
    val extension: String
) {
}