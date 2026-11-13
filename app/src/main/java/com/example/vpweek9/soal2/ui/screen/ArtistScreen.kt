package com.example.vpweek9.soal2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.vpweek9.soal2.ui.widgets.AlbumCard
import com.example.vpweek9.soal2.ui.widgets.ArtistInfo
import com.example.vpweek9.soal2.ui.widgets.CountBadge
import com.example.vpweek9.soal2.ui.widgets.ErrorBox
import com.example.vpweek9.soal2.ui.widgets.LoadingBox
import com.example.vpweek9.soal2.ui.widgets.MusicBg
import com.example.vpweek9.soal2.ui.widgets.MusicBorder
import com.example.vpweek9.soal2.ui.widgets.MusicGreen
import com.example.vpweek9.soal2.ui.widgets.MusicHeader
import com.example.vpweek9.soal2.ui.widgets.MusicMuted
import com.example.vpweek9.soal2.ui.widgets.MusicSurface
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

    Surface(Modifier.fillMaxSize(), color = MusicBg, contentColor = Color.White) {
        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MusicHeader()

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MusicGreen
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Filled.Close, contentDescription = "Clear", tint = MusicMuted)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MusicSurface,
                    unfocusedContainerColor = MusicSurface,
                    focusedBorderColor = MusicGreen,
                    unfocusedBorderColor = MusicBorder,
                    cursorColor = MusicGreen,
                ),
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
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ArtistInfo(
                            artist = artist,
                            expanded = expanded,
                            onToggle = { expanded = !expanded })
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    "Diskografi Album",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Koleksi rilisan resmi", fontSize = 12.sp, color = MusicMuted)
                            }
                            CountBadge("${uiState.albums.size} Album")
                        }
                    }
                    items(uiState.albums) { album ->
                        AlbumCard(album = album, onClick = { onAlbumClick(album.id) })
                    }
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