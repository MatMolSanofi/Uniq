package com.molette.uniq.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.data.db.models.ContactDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao: BaseDao<ContactDb> {

    @Query("SELECT * FROM contacts ORDER BY name")
    fun getAll(): Flow<List<ContactDb>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContact(id: Long): Flow<ContactDb>

    @Query("DELETE FROM contacts")
    suspend fun clear()
}