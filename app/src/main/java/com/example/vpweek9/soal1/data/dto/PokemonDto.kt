package com.example.vpweek9.soal1.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponseDto(
    val data: List<PokemonCardDto> = emptyList(),
)

@Serializable
data class PokemonCardDto(
    val name: String = "",
    val setName: String? = null,
    val cardNumber: String? = null,
    val rarity: String? = null,
    val cardType: String? = null,
    val types: List<String>? = null,
    val artist: String? = null,
    val imageCdnUrl: String? = null,
    val prices: PokemonPricesDto? = null,
)

@Serializable
data class PokemonPricesDto(
    val market: Double? = null,
    val low: Double? = null,
    val lowPrice: Double? = null,
    val sellers: Int? = null,
    val listings: Int? = null,
    val totalListings: Int? = null,
    val primaryPrinting: String? = null,
)