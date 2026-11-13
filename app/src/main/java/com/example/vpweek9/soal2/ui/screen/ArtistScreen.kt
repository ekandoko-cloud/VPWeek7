package com.example.vpweek9.soal2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.vpweek9.soal2.ui.widgets.AlbumCard
import com.example.vpweek9.soal2.ui.widgets.ArtistInfo
import com.example.vpweek9.soal2.ui.widgets.ErrorBox
import com.example.vpweek9.soal2.ui.widgets.LoadingBox
import com.example.vpweek9.soal2.viewmodel.ArtistViewModel

@Composable
fun ArtistScreen(
    onAlbumClick: (String) -> Unit,
    viewModel: ArtistViewModel = viewModel(factory = ArtistViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("Coldplay") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val artist = uiState.artist

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Music Artist", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF1DB954)
                )
            },
            trailingIcon = {
                IconButton(onClick = { query = "" }) {
                    Icon(Icons.Filled.Close, contentDescription = "Clear")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.search(query) }),
        )

        when {
            uiState.isLoading -> LoadingBox()
            uiState.error != null -> ErrorBox(
                uiState.error.orEmpty(),
                onRetry = { viewModel.search(query) })

            artist != null -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ArtistInfo(
                        artist = artist,
                        expanded = expanded,
                        onToggle = { expanded = !expanded })
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text("Diskografi Album", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("Koleksi rilisan resmi", fontSize = 12.sp)
                        }
                        Text(
                            "${uiState.albums.size} Album",
                            color = Color(0xFF1DB954),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(uiState.albums) { album ->
                    AlbumCard(album = album, onClick = { onAlbumClick(album.id) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistScreenPreview() {
    ArtistScreen(onAlbumClick = {})
}