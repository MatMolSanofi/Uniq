package com.molette.uniq.domain.usecases.characters

import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterUseCase(
    private val characterRepository: CharacterRepository
) {

    fun getCharacter(characterId: Long): Flow<CharacterDb>{
        return characterRepository.getCharacter(characterId)
    }
}