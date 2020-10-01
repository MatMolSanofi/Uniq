package com.molette.uniq

import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.data.db.models.toCharacter
import com.molette.uniq.data.network.models.CharacterRemote
import com.molette.uniq.data.network.models.ThumbnailRemote
import com.molette.uniq.data.network.models.toCharacterDb
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Test

class CharacterMapperUnitTest {

    val characterRemote = CharacterRemote(
        id = 1,
        name = "Hulk",
        description = "Remote description",
        thumbnail = ThumbnailRemote(
            path = "path/test",
            extension = "jpg"
        )
    )

    val characterDb = CharacterDb(
        id = 35,
        name = "Ant man",
        description = "Db description",
        thumbnail = "thumbnail",
        characterId = 5678,
        extension = "png"
    )

    @Test
    fun characterRemoteMapping_isCorrect(){

        val db = characterRemote.toCharacterDb()

        Assert.assertThat(db.characterId, IsEqual(characterRemote.id))
        Assert.assertThat(db.name, IsEqual(characterRemote.name))
        Assert.assertThat(db.description, IsEqual(characterRemote.description))
        Assert.assertThat(db.thumbnail, IsEqual(characterRemote.thumbnail.path))
        Assert.assertThat(db.extension, IsEqual(characterRemote.thumbnail.extension))
    }

    @Test
    fun characterDbMapping_isCorrect(){

        val local = characterDb.toCharacter()

        Assert.assertThat(local.id, IsEqual(characterDb.id))
        Assert.assertThat(local.characterId, IsEqual(characterDb.characterId))
        Assert.assertThat(local.name, IsEqual(characterDb.name))
        Assert.assertThat(local.description, IsEqual(characterDb.description))
        Assert.assertThat(local.thumbnail, IsEqual(characterDb.thumbnail))
        Assert.assertThat(local.extension, IsEqual(characterDb.extension))
    }
}