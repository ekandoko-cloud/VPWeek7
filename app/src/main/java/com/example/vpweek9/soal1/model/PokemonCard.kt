package com.example.vpweek9.soal1.model

data class PokemonCard(
    val name: String,
    val setName: String,
    val cardNumber: String,
    val rarity: String,
    val type: String,
    val illustrator: String,
    val primaryPrinting: String,
    val imageUrl: String?,
    val marketPrice: Double?,
    val lowPrice: Double?,
    val activeListings: Int?,
    val verifiedSellers: Int?,
)