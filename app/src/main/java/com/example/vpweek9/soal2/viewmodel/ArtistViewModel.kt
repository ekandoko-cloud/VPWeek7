package com.example.vpweek9.soal2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vpweek9.soal2.data.remote.ServiceLocator
import com.example.vpweek9.soal2.data.repository.MusicRepository
import com.example.vpweek9.soal2.model.Album
import com.example.vpweek9.soal2.model.Artist
import com.example.vpweek9.soal2.util.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ArtistUiState(
    val artist: Artist? = null,
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

class ArtistViewModel(
    private val repository: MusicRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistUiState())
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

    init {
        search("Coldplay")
    }

    fun search(name: String) {
        _uiState.value = ArtistUiState(isLoading = true)
        viewModelScope.launch {
            repository.getArtist(name)
                .onSuccess { artist ->
                    val albums =
                        repository.getAlbums(artist.id, artist.name).getOrDefault(emptyList())
                    _uiState.value = ArtistUiState(artist = artist, albums = albums)
                }
                .onFailure { e -> _uiState.value = ArtistUiState(error = e.toUserMessage()) }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer { ArtistViewModel(ServiceLocator.musicRepository) }
        }
    }
}