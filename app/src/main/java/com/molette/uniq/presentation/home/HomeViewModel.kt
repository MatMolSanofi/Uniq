package com.molette.uniq.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.molette.uniq.data.db.models.toCharacter
import com.molette.uniq.data.db.models.toContact
import com.molette.uniq.domain.usecases.characters.GetCharactersUseCase
import com.molette.uniq.domain.usecases.contacts.GetContactsUseCase
import com.molette.uniq.presentation.models.Character
import com.molette.uniq.presentation.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {

    var characters: Flow<PagingData<Character>>? = null
    var contacts: LiveData<List<Contact>> = getContactsUseCase.getContacts().map { it.map { it.toContact() } }.asLiveData()

    fun getCharactersPaged(): Flow<PagingData<Character>>? {
        var newCharactersPaged = getCharactersUseCase.getCharacters().cachedIn(viewModelScope)
        characters = newCharactersPaged.map { items -> items.map { it.toCharacter() } }
        return characters
    }

    fun getContacts(){
        viewModelScope.launch{
            getContactsUseCase.getContactsFromDevice()
        }
    }
}