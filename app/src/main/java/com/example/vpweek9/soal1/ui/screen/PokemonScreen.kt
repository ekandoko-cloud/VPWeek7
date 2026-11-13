package com.example.vpweek9.soal1.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek9.soal1.model.PokemonCard
import com.example.vpweek9.soal1.ui.widgets.ErrorBox
import com.example.vpweek9.soal1.ui.widgets.LoadingBox
import com.example.vpweek9.soal1.ui.widgets.PokeBg
import com.example.vpweek9.soal1.ui.widgets.PokeBorder
import com.example.vpweek9.soal1.ui.widgets.PokeCard
import com.example.vpweek9.soal1.ui.widgets.PokeHeader
import com.example.vpweek9.soal1.ui.widgets.PokeMuted
import com.example.vpweek9.soal1.ui.widgets.PokeRed
import com.example.vpweek9.soal1.ui.widgets.PokemonCardItem
import com.example.vpweek9.soal1.ui.widgets.PokemonDetailSheet
import com.example.vpweek9.soal1.viewmodel.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = viewModel(factory = PokemonViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("Charizard") }
    var selectedCard by remember { mutableStateOf<PokemonCard?>(null) }

    Surface(Modifier.fillMaxSize(), color = PokeBg, contentColor = Color.White) {
        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PokeHeader()

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search, contentDescription = "Search", tint = PokeRed
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Filled.Close, contentDescription = "Clear", tint = PokeMuted)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PokeCard,
                    unfocusedContainerColor = PokeCard,
                    focusedBorderColor = PokeRed,
                    unfocusedBorderColor = PokeBorder,
                    cursorColor = PokeRed,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.search(query) }),
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("Charizard", "Pikachu", "Mewtwo", "Gengar", "Rayquaza", "Hydreigon", "Groudon", "Zekrom", "Reshiram", "Eternatus")) { name ->
                    Surface(
                        onClick = {
                            query = name
                            viewModel.search(name)
                        },
                        shape = RoundedCornerShape(20.dp),
                        color = PokeCard,
                        border = BorderStroke(1.dp, PokeBorder),
                    ) {
                        Text(
                            text = name,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            fontSize = 13.sp,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "Market Results (${uiState.cards.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text("USD · Near Mint", fontSize = 12.sp, color = PokeMuted)
            }

            when {
                uiState.isLoading -> LoadingBox()
                uiState.error != null -> ErrorBox(
                    uiState.error.orEmpty(),
                    onRetry = { viewModel.search(query) })

                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(uiState.cards) { card ->
                        PokemonCardItem(card = card, onClick = { selectedCard = card })
                    }
                }
            }
        }
    }

    selectedCard?.let { card ->
        PokemonDetailSheet(card = card, onDismiss = { selectedCard = null })
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PokemonScreen()
}