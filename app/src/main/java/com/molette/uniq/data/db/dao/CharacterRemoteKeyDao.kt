package com.molette.uniq.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.molette.uniq.data.db.models.CharacterRemoteKeyDb

@Dao
interface CharacterRemoteKeyDao: BaseDao<CharacterRemoteKeyDb> {

    @Query("SELECT * FROM character_remote_keys WHERE characterId = :id")
    suspend fun remoteKey(id: Long): CharacterRemoteKeyDb

    @Query("DELETE FROM character_remote_keys")
    suspend fun clear()
}