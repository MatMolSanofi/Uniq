package com.molette.uniq.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class MarvelResponse<T>(
    @SerialName("data")
    val page: Page<T>
) {

}