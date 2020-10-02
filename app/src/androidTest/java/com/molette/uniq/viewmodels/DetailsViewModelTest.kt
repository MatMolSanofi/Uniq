package com.molette.uniq.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.molette.uniq.core.di.appModule
import com.molette.uniq.core.di.dbModule
import com.molette.uniq.data.db.AppDatabase
import com.molette.uniq.data.db.models.CharacterDb
import com.molette.uniq.presentation.details.DetailsViewModel
import com.molette.uniq.presentation.models.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@RunWith(JUnit4::class)
class DetailsViewModelTest: KoinTest {

    val detailsViewModel: DetailsViewModel by inject()
    val db: AppDatabase by inject()
    lateinit var characterDb: CharacterDb
    lateinit var observer: Observer<Character>

    companion object{
        const val CHARACTER_ID: Long = 457
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val koinTestRule = KoinTestRule.create {
        modules(appModule, dbModule)
    }

    @Before
    fun setup(){
        insertCharacter()
    }

    @Test
    fun getCharacter_isCorrect() {
        GlobalScope.launch(Dispatchers.Main) {
            observer = Observer {
                assert(it.id == CHARACTER_ID)
            }
            detailsViewModel.character.observeForever(observer)
            detailsViewModel.characterId.value = CHARACTER_ID
        }
    }

    @After
    fun after(){
        GlobalScope.launch(Dispatchers.Main){
            detailsViewModel.character.removeObserver(observer)
        }
        db.close()
    }

    private fun buildCharacter(): CharacterDb{
        return CharacterDb(
            id = 0,
            name = "Ant man",
            description = "Db description",
            thumbnail = "thumbnail",
            characterId = CHARACTER_ID,
            extension = "png"
        )
    }

    private fun insertCharacter(){
        GlobalScope.launch(Dispatchers.IO) {
            characterDb = buildCharacter()
            db.characterDao.insert(characterDb)
        }
    }
}