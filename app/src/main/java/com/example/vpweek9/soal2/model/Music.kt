package com.example.vpweek9.soal2.model

data class Artist(
    val id: String,
    val name: String,
    val genre: String,
    val country: String,
    val biography: String,
    val thumbUrl: String?,
    val bannerUrl: String?,
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val year: String,
    val genre: String,
    val coverUrl: String?,
    val description: String,
)

data class Track(
    val number: String,
    val title: String,
    val duration: String,
    val genre: String,
)