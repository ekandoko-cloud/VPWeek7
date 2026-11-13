package com.example.vpweek9.soal2.data.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ArtistResponseDto(
    val artists: List<ArtistDto>? = null,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class ArtistDto(
    @SerialName("idArtist") val id: String = "",
    @SerialName("strArtist") val name: String = "",
    @SerialName("strGenre") val genre: String? = null,
    @SerialName("strCountry") val country: String? = null,
    @SerialName("strBiography") @JsonNames("strBiographyEN") val biography: String? = null,
    @SerialName("strArtistThumb") val thumb: String? = null,
    @SerialName("strArtistBanner") val banner: String? = null,
    @SerialName("strArtistFanart") val fanart: String? = null,
)

@Serializable
data class AlbumResponseDto(
    val album: List<AlbumDto>? = null,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class AlbumDto(
    @SerialName("idAlbum") val id: String = "",
    @SerialName("strAlbum") val title: String = "",
    @SerialName("strArtist") val artist: String? = null,
    @SerialName("intYearReleased") val year: String? = null,
    @SerialName("strGenre") val genre: String? = null,
    @SerialName("strAlbumThumb") val cover: String? = null,
    @SerialName("strDescription") @JsonNames("strDescriptionEN") val description: String? = null,
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