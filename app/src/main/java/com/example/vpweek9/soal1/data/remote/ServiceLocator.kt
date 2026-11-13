package com.example.vpweek9.soal1.data.remote

import com.example.vpweek9.soal1.data.repository.PokemonRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ServiceLocator {
    private const val BASE_URL = "https://www.pokemonpricetracker.com/api/v2/"
    private val API_KEYS = listOf(
        "pokeprice_free_182787a4a2733ab5ceaaae66efca91a57817120f57260c6c",
        "pokeprice_free_4f9d50dc94ffdfc0a5de6fafae4daec07099b88fb8536adf",
        "pokeprice_free_3bc8982dfee2fe64e5d272da83d3663d0b47e822cf52eca1",
    )

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(API_KEYS))
            .build()
    }

    private val api: PokemonApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PokemonApi::class.java)
    }

    val pokemonRepository: PokemonRepository by lazy { PokemonRepository(api) }
}