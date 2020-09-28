package com.molette.uniq.data.network

import com.molette.uniq.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val originalUrl = request.url()

        var url = originalUrl.newBuilder()
            .addQueryParameter("apikey", BuildConfig.MARVEL_API_KEY)
            .build()

        var requestBuilder = request.newBuilder().url(url)

        var newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}