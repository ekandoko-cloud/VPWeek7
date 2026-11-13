package com.example.vpweek9.soal1.data.repository

import com.example.vpweek9.soal1.data.dto.PokemonCardDto
import com.example.vpweek9.soal1.data.dto.PokemonListResponseDto
import com.example.vpweek9.soal1.model.PokemonCard

fun PokemonCardDto.toDomain() = PokemonCard(
    name = name,
    setName = setName.orEmpty(),
    cardNumber = cardNumber.orEmpty(),
    rarity = rarity.orEmpty(),
    type = (cardType ?: types?.firstOrNull()).orEmpty(),
    illustrator = artist.orEmpty(),
    primaryPrinting = prices?.primaryPrinting.orEmpty(),
    imageUrl = imageCdnUrl,
    marketPrice = prices?.market,
    lowPrice = prices?.low ?: prices?.lowPrice,
    activeListings = prices?.listings ?: prices?.totalListings,
    verifiedSellers = prices?.sellers,
)

fun PokemonListResponseDto.toDomain(): List<PokemonCard> = data.map { it.toDomain() }