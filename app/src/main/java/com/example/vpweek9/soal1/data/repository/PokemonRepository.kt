package com.example.vpweek9.soal1.data.repository

import com.example.vpweek9.soal1.data.remote.PokemonApi
import com.example.vpweek9.soal1.model.PokemonCard
import com.example.vpweek9.soal1.util.safeCall

class PokemonRepository(private val api: PokemonApi) {
    suspend fun searchCards(name: String): Result<List<PokemonCard>> = safeCall {
        api.searchCards(name).toDomain()
    }
}