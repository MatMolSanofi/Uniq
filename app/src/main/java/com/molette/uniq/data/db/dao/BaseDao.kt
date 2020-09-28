package com.molette.uniq.data.db.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(obj: List<T?>): LongArray

    @Delete
    fun delete(obj: T)

    @Update
    fun update(obj: T)
}