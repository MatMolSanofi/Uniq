package com.molette.uniq.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.molette.uniq.data.db.models.CharacterDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao: BaseDao<CharacterDb> {

    @Query
    ("SELECT * FROM characters")
    fun getAll(): PagingSource<Int, CharacterDb>

    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    fun getCharacter(characterId: Long): Flow<CharacterDb>

    @Query("DELETE FROM characters")
    suspend fun clear()
}