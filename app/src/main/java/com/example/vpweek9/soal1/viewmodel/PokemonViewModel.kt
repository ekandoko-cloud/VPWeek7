package com.example.vpweek9.soal1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vpweek9.soal1.data.remote.ServiceLocator
import com.example.vpweek9.soal1.model.PokemonCard
import com.example.vpweek9.soal1.data.repository.PokemonRepository
import com.example.vpweek9.soal1.util.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PokemonUiState(
    val cards: List<PokemonCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

class PokemonViewModel(
    private val repository: PokemonRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()

    init {
        search("Charizard")
    }

    fun search(name: String) {
        _uiState.value = PokemonUiState(isLoading = true)
        viewModelScope.launch {
            repository.searchCards(name)
                .onSuccess { cards -> _uiState.value = PokemonUiState(cards = cards) }
                .onFailure { e -> _uiState.value = PokemonUiState(error = e.toUserMessage()) }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer { PokemonViewModel(ServiceLocator.pokemonRepository) }
        }
    }
}