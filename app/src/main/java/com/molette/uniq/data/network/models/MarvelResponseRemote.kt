package com.molette.uniq.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class MarvelResponseRemote<T>(
    @SerialName("data")
    val page: PageRemote<T>
) {

}