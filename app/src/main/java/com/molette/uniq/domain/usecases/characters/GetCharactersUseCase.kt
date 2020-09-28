package com.molette.uniq.domain.usecases.characters

import androidx.paging.PagingData
import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val characterRepository: CharacterRepository
) {

    fun getCharacters(): Flow<PagingData<CharacterDb>> {
        return characterRepository.getCharactersPaged()
    }
}