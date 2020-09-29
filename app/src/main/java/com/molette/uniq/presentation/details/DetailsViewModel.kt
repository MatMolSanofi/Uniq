package com.molette.uniq.presentation.details

import androidx.lifecycle.*
import com.molette.uniq.data.db.models.toCharacter
import com.molette.uniq.domain.usecases.characters.GetCharacterUseCase
import com.molette.uniq.presentation.models.Character
import kotlinx.coroutines.flow.map

class DetailsViewModel(
    private val getCharacterUseCase: GetCharacterUseCase
): ViewModel() {

    var characterId = MutableLiveData<Long>()

    val character: LiveData<Character> = characterId.switchMap {
        getCharacterUseCase.getCharacter(it).map { it.toCharacter() }.asLiveData()
    }
}