package com.molette.uniq.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.molette.uniq.data.db.models.toCharacter
import com.molette.uniq.domain.usecases.characters.GetCharactersUseCase
import com.molette.uniq.presentation.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
): ViewModel() {

    var characters: Flow<PagingData<Character>>? = null

    fun getCharactersPaged(): Flow<PagingData<Character>>? {
        characters = getCharactersUseCase.getCharacters().map { items -> items.map { it.toCharacter() } }.cachedIn(viewModelScope)
        return characters
    }
}