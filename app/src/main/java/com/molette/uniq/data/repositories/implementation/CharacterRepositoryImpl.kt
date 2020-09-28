package com.molette.uniq.data.repositories.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.molette.uniq.data.db.AppDatabase
import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.data.network.MarvelAPI
import com.molette.uniq.data.repositories.mediators.CharacterRemoteMediator
import com.molette.uniq.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val localDataSource: AppDatabase,
    private val remoteDataSource: MarvelAPI
): CharacterRepository {

    val pagingSourceFactory =  { localDataSource.characterDao.getAll()}

    override fun getCharactersPaged(): Flow<PagingData<CharacterDb>> {
        return Pager(
            config = PagingConfig(
                pageSize = CharacterRemoteMediator.PAGE_SIZE,
                enablePlaceholders = false),
            remoteMediator = CharacterRemoteMediator(remoteDataSource, localDataSource),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}