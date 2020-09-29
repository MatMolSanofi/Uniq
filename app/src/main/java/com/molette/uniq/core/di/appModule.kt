package com.molette.uniq.core.di

import com.molette.uniq.data.repositories.implementation.CharacterRepositoryImpl
import com.molette.uniq.domain.repositories.CharacterRepository
import com.molette.uniq.domain.usecases.characters.GetCharacterUseCase
import com.molette.uniq.domain.usecases.characters.GetCharactersUseCase
import com.molette.uniq.presentation.details.DetailsViewModel
import com.molette.uniq.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var appModule = module {
    // Repositories
    factory<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }

    // ViewModels
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

    // Use Cases
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterUseCase(get()) }
}