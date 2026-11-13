package com.example.vpweek9.soal2.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistResponseDto(
    val artists: List<ArtistDto>? = null,
)

@Serializable
data class ArtistDto(
    @SerialName("strArtist") val name: String = "",
    @SerialName("strGenre") val genre: String? = null,
    @SerialName("strCountry") val country: String? = null,
    @SerialName("strBiographyEN") val biography: String? = null,
    @SerialName("strArtistThumb") val thumb: String? = null,
    @SerialName("strArtistBanner") val banner: String? = null,
    @SerialName("strArtistFanart") val fanart: String? = null,
)

@Serializable
data class AlbumResponseDto(
    val album: List<AlbumDto>? = null,
)

@Serializable
data class AlbumDto(
    @SerialName("idAlbum") val id: String = "",
    @SerialName("strAlbum") val title: String = "",
    @SerialName("strArtist") val artist: String? = null,
    @SerialName("intYearReleased") val year: String? = null,
    @SerialName("strGenre") val genre: String? = null,
    @SerialName("strAlbumThumb") val cover: String? = null,
    @SerialName("strDescriptionEN") val description: String? = null,
)

@Serializable
data class TrackResponseDto(
    val track: List<TrackDto>? = null,
)

@Serializable
data class TrackDto(
    @SerialName("intTrackNumber") val number: String? = null,
    @SerialName("strTrack") val title: String = "",
    @SerialName("intDuration") val duration: String? = null,
    @SerialName("strGenre") val genre: String? = null,
)