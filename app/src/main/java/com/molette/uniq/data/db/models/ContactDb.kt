package com.molette.uniq.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.molette.uniq.presentation.models.Contact

@Entity(tableName = "contacts")
data class ContactDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val contactId: Long,
    val name: String,
    val phone: String,
    val thumbnail: String
) {
}

fun ContactDb.toContact(): Contact {
    return Contact(id, contactId, name, phone, thumbnail)
}