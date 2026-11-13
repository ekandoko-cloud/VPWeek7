package com.example.vpweek9.soal2.data.remote

import com.example.vpweek9.soal2.data.dto.AlbumResponseDto
import com.example.vpweek9.soal2.data.dto.ArtistResponseDto
import com.example.vpweek9.soal2.data.dto.TrackResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    @GET("search.php")
    suspend fun searchArtist(@Query("s") name: String): ArtistResponseDto
    @GET("searchalbum.php")
    suspend fun searchAlbums(@Query("s") name: String): AlbumResponseDto
    @GET("album.php")
    suspend fun getAlbumsByArtist(@Query("i") artistId: String): AlbumResponseDto
    @GET("album.php")
    suspend fun getAlbum(@Query("m") albumId: String): AlbumResponseDto
    @GET("track.php")
    suspend fun getTracks(@Query("m") albumId: String): TrackResponseDto
}