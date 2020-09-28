package com.molette.uniq.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.molette.uniq.data.db.dao.CharacterDao
import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.data.db.models.CharacterRemoteKeyDb
import kotlinx.serialization.ImplicitReflectionSerializer

@OptIn(ImplicitReflectionSerializer::class)
@Database(entities = [CharacterDb::class, CharacterRemoteKeyDb::class],
    version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val characterDao: CharacterDao
    abstract val characterRemoteKeyDao: CharacterRemoteKeyDb

    companion object{
        private const val DB_NAME = "uniq_db"

        fun buildDb(context: Context): AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("buildDb", "Db created")
                    }
                })
                .build()
        }
    }
}