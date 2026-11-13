package com.example.vpweek9.soal2.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.example.vpweek9.soal2.data.repository.MusicRepository
import com.example.vpweek9.soal2.model.Album
import com.example.vpweek9.soal2.model.Track
import com.example.vpweek9.soal2.navigation.AlbumDetailRoute
import com.example.vpweek9.soal2.data.remote.ServiceLocator
import com.example.vpweek9.soal2.util.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AlbumDetailUiState(
    val album: Album? = null,
    val tracks: List<Track> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)

class AlbumDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: MusicRepository,
) : ViewModel() {

    private val albumId: String = savedStateHandle.toRoute<AlbumDetailRoute>().albumId

    private val _uiState = MutableStateFlow(AlbumDetailUiState())
    val uiState: StateFlow<AlbumDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.value = AlbumDetailUiState(isLoading = true)
        viewModelScope.launch {
            repository.getAlbumDetail(albumId)
                .onSuccess { album ->
                    val tracks = repository.getTracks(albumId).getOrDefault(emptyList())
                    _uiState.value =
                        AlbumDetailUiState(album = album, tracks = tracks, isLoading = false)
                }
                .onFailure { e ->
                    _uiState.value =
                        AlbumDetailUiState(isLoading = false, error = e.toUserMessage())
                }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                AlbumDetailViewModel(createSavedStateHandle(), ServiceLocator.musicRepository)
            }
        }
    }
}