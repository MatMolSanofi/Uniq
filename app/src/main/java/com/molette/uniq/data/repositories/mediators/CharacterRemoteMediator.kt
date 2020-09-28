package com.molette.uniq.data.repositories.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.molette.uniq.data.db.AppDatabase
import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.data.db.models.CharacterRemoteKeyDb
import com.molette.uniq.data.network.MarvelAPI
import com.molette.uniq.data.network.models.toCharacterDb
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    val remoteSource: MarvelAPI,
    val localSource: AppDatabase
) : RemoteMediator<Int, CharacterDb>() {

    companion object {
        const val PAGE_SIZE = 20
        const val STARTING_OFFSET = 0
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterDb>): CharacterRemoteKeyDb? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                // Get the remote keys of the last item retrieved
                localSource.characterRemoteKeyDao.remoteKey(character.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterDb>): CharacterRemoteKeyDb? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                // Get the remote keys of the first items retrieved
                localSource.characterRemoteKeyDao.remoteKey(character.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterDb>
    ): CharacterRemoteKeyDb? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                localSource.characterRemoteKeyDao.remoteKey(characterId)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterDb>
    ): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_OFFSET
            }
            LoadType.PREPEND -> {

                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                    TODO("Handle crash first time")
//                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.previousKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.previousKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                    TODO("Handle crash first time")
                    //throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {
            val response = remoteSource.getCharacters(limit = PAGE_SIZE, offset = offset)

            val characters = response.page.results
            val endOfPaginationReached = characters.isEmpty() || offset == response.page.total
            localSource.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    localSource.characterRemoteKeyDao.clear()
                    localSource.characterDao.clear()
                }
                val prevKey = if (offset == STARTING_OFFSET) null else offset - PAGE_SIZE
                val nextKey = if (endOfPaginationReached) null else offset + PAGE_SIZE
                val keys = characters.map {
                    CharacterRemoteKeyDb(
                        characterId = it.id,
                        previousKey = prevKey,
                        nextKey = nextKey
                    )
                }
                localSource.characterRemoteKeyDao.insertAll(keys)
                localSource.characterDao.insertAll(characters.map {
                    var characterDb = it.toCharacterDb()
                    return@map characterDb
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}