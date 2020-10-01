package com.molette.uniq.domain.repositories

import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.data.db.models.ContactDb
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    fun getContacts(): Flow<List<ContactDb>>
    suspend fun getContactsFromDevice()
}