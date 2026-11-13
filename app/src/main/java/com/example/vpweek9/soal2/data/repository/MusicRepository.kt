package com.example.vpweek9.soal2.data.repository

import com.example.vpweek9.soal2.data.remote.MusicApi
import com.example.vpweek9.soal2.model.Album
import com.example.vpweek9.soal2.model.Artist
import com.example.vpweek9.soal2.model.Track
import com.example.vpweek9.soal2.util.safeCall

class MusicRepository(private val api: MusicApi) {

    suspend fun getArtist(name: String): Result<Artist> = safeCall {
        api.searchArtist(name).artists?.firstOrNull()?.toDomain()
            ?: throw NoSuchElementException("Artis \"$name\" tidak ditemukan")
    }

    suspend fun getAlbums(artistId: String, artistName: String): Result<List<Album>> = safeCall {
        val byId = api.getAlbumsByArtist(artistId).album.orEmpty()
        val albums = byId.ifEmpty { api.searchAlbums(artistName).album.orEmpty() }
        albums.map { it.toDomain() }
    }

    suspend fun getAlbumDetail(albumId: String): Result<Album> = safeCall {
        api.getAlbum(albumId).album?.firstOrNull()?.toDomain()
            ?: throw NoSuchElementException("Album tidak ditemukan")
    }

    suspend fun getTracks(albumId: String): Result<List<Track>> = safeCall {
        api.getTracks(albumId).track.orEmpty().map { it.toDomain() }
    }
}