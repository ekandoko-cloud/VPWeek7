package com.example.vpweek9.soal1.data.remote

import com.example.vpweek9.soal1.data.dto.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("cards")
    suspend fun searchCards(
        @Query("search") name: String,
        @Query("limit") limit: Int = 20,
    ): PokemonListResponseDto
}