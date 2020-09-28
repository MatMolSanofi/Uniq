package com.molette.uniq.data.network

import com.molette.uniq.data.network.models.Character
import com.molette.uniq.data.network.models.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    companion object{
        private const val OFFSET_PARAM = "offset"
        private const val LIMIT_PARAM = "limit"
        private const val ORDER_BY_PARAM = "orderBy"
    }

    @GET("characters/")
    suspend fun getCharacters(
        @Query("$ORDER_BY_PARAM") orderBy: String = "name",
        @Query("$LIMIT_PARAM") limit: Int = 20,
        @Query("$OFFSET_PARAM") offset: Int = 0
    ): MarvelResponse<Character>
}