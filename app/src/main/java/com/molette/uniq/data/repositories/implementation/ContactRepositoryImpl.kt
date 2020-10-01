package com.molette.uniq.data.repositories.implementation

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.molette.uniq.R
import com.molette.uniq.data.db.AppDatabase
import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.data.db.models.ContactDb
import com.molette.uniq.domain.repositories.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ContactRepositoryImpl(
    private val context: Context,
    private val localDataSource: AppDatabase
): ContactRepository {

    override fun getContacts(): Flow<List<ContactDb>> {
        return localDataSource.contactDao.getAll()
    }

    override suspend fun getContactsFromDevice() {

        withContext(Dispatchers.IO){
            val contacts : MutableList<ContactDb> = mutableListOf()
            val cursor = context.contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                null,
                null,
                "display_name ASC"
            )
            cursor?.let {
                if(cursor.count > 0){
                    while (cursor.moveToNext()){

                        val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) ?: ""
                        var phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ?: ""
                        val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                        val photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)) ?: ""
                        if(phone == name){
                            phone = context.getString(R.string.unavailable)
                        }
                        Log.d("getContactsFromDevice", "Name: $name")
                        Log.d("getContactsFromDevice", "Phone: $phone")
                        Log.d("getContactsFromDevice", "Photo: $photo")
                        contacts.add(ContactDb(0, contactId, name, phone, photo))
                    }
                    cursor.close()
                }
            }

            localDataSource.contactDao.insertAll(contacts)
        }
    }
}