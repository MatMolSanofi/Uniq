package com.molette.uniq

import android.app.Application
import com.molette.uniq.core.di.appModule
import com.molette.uniq.core.di.dbModule
import com.molette.uniq.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule, dbModule, networkModule)
        }
    }
}