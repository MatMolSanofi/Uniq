package com.molette.uniq.domain.repositories

import androidx.paging.PagingData
import com.molette.uniq.data.db.models.CharacterDb
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharactersPaged(): Flow<PagingData<CharacterDb>>
    fun getCharacter(characterId: Long): Flow<CharacterDb>
}