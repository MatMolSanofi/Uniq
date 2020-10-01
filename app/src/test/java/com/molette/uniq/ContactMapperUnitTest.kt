package com.molette.uniq

import com.molette.uniq.data.db.models.ContactDb
import com.molette.uniq.data.db.models.toContact
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Test

class ContactMapperUnitTest {

    val contactDb = ContactDb(
        id = 12,
        name = "Mathieu Molette",
        thumbnail = "thumbnail",
        phone = "06 24 09 30 70",
        contactId = 56
    )

    @Test
    fun contactDbMapping_isCorrect(){

        val local = contactDb.toContact()

        Assert.assertThat(local.id, IsEqual(contactDb.id))
        Assert.assertThat(local.contactId, IsEqual(contactDb.contactId))
        Assert.assertThat(local.name, IsEqual(contactDb.name))
        Assert.assertThat(local.phone, IsEqual(contactDb.phone))
        Assert.assertThat(local.thumbnail, IsEqual(contactDb.thumbnail))
    }
}
