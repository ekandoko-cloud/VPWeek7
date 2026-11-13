package com.example.vpweek9.soal1.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor(private val apiKeys: List<String>) : Interceptor {
    @Volatile
    private var index = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(withKey(chain.request(), apiKeys[index]))
        var tries = 1
        while (response.code == 429 && tries < apiKeys.size) {
            response.close()
            index = (index + 1) % apiKeys.size
            response = chain.proceed(withKey(chain.request(), apiKeys[index]))
            tries++
        }
        return response
    }

    private fun withKey(request: Request, apiKey: String): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .build()
}