package com.molette.uniq.data.network

import android.util.Log
import com.molette.uniq.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.util.*

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val originalUrl = request.url()

        val timestamp = Date().time.toString()
        val stringToHash = timestamp + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_API_KEY

        var url = originalUrl.newBuilder()
            .addQueryParameter("apikey", BuildConfig.MARVEL_API_KEY)
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("hash", stringToHash.toMD5())
            .build()

        var requestBuilder = request.newBuilder().url(url)

        var newRequest = requestBuilder.build()
        Log.d("ApiKeyInterceptor", newRequest.url().toString())
        return chain.proceed(newRequest)
    }
}

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}