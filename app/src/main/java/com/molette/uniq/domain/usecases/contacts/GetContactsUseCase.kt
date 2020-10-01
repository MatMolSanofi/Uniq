package com.molette.uniq.domain.usecases.contacts

import com.molette.uniq.data.db.models.ContactDb
import com.molette.uniq.domain.repositories.ContactRepository
import kotlinx.coroutines.flow.Flow

class GetContactsUseCase(
    private val contactRepository: ContactRepository
) {

    fun getContacts(): Flow<List<ContactDb>> {
        return  contactRepository.getContacts()
    }

    suspend fun getContactsFromDevice(){
        contactRepository.getContactsFromDevice()
    }
}