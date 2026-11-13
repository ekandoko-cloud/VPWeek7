package com.example.vpweek9.soal2.data.repository

import com.example.vpweek9.soal2.data.dto.AlbumDto
import com.example.vpweek9.soal2.data.dto.ArtistDto
import com.example.vpweek9.soal2.data.dto.TrackDto
import com.example.vpweek9.soal2.model.Album
import com.example.vpweek9.soal2.model.Artist
import com.example.vpweek9.soal2.model.Track
import java.util.Locale

fun ArtistDto.toDomain() = Artist(
    name = name,
    genre = genre.orEmpty(),
    country = country.orEmpty(),
    biography = biography.orEmpty(),
    imageUrl = banner ?: fanart ?: thumb,
)

fun AlbumDto.toDomain() = Album(
    id = id,
    title = title,
    artist = artist.orEmpty(),
    year = year.orEmpty(),
    genre = genre.orEmpty(),
    coverUrl = cover,
    description = description.orEmpty(),
)

fun TrackDto.toDomain(): Track {
    val seconds = (duration?.toLongOrNull() ?: 0L) / 1000
    return Track(
        number = number.orEmpty().padStart(2, '0'),
        title = title,
        duration = String.format(Locale.US, "%02d:%02d", seconds / 60, seconds % 60),
        genre = genre.orEmpty(),
    )
}