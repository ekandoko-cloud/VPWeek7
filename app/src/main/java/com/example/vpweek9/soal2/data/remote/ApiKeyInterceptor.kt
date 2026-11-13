package com.example.vpweek9.soal2.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val segments = original.url.encodedPathSegments

        val url = original.url.newBuilder()
            .encodedPath("/")
            .apply {
                segments.dropLast(1).forEach { addEncodedPathSegment(it) }
                addPathSegment(apiKey)
                addEncodedPathSegment(segments.last())
            }
            .build()

        return chain.proceed(original.newBuilder().url(url).build())
    }
}