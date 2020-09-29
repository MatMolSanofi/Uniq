package com.molette.uniq.core.di

import com.molette.uniq.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var dbModule = module {
    single {
        AppDatabase.buildDb(androidContext())
    }
}