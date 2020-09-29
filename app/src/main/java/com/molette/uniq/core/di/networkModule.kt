package com.molette.uniq.core.di

import com.molette.uniq.data.network.ApiKeyInterceptor
import com.molette.uniq.data.network.RestClient
import org.koin.dsl.module

var networkModule = module {

    single { ApiKeyInterceptor() }
    single { RestClient.createOkHttpClient(get()) }
    single { RestClient.getRetrofitInstance(get()) }
    single { RestClient.createMarvelAPI(get()) }
}