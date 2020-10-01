package com.molette.uniq.core.di

import com.molette.uniq.data.repositories.implementation.CharacterRepositoryImpl
import com.molette.uniq.data.repositories.implementation.ContactRepositoryImpl
import com.molette.uniq.domain.repositories.CharacterRepository
import com.molette.uniq.domain.repositories.ContactRepository
import com.molette.uniq.domain.usecases.characters.GetCharacterUseCase
import com.molette.uniq.domain.usecases.characters.GetCharactersUseCase
import com.molette.uniq.domain.usecases.contacts.GetContactsUseCase
import com.molette.uniq.presentation.details.DetailsViewModel
import com.molette.uniq.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var appModule = module {
    // Repositories
    factory<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
    factory<ContactRepository> { ContactRepositoryImpl(get(), get()) }

    // ViewModels
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }

    // Use Cases
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterUseCase(get()) }
    factory { GetContactsUseCase(get()) }
}