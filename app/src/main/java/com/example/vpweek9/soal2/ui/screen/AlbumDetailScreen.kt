package com.example.vpweek9.soal2.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.vpweek9.soal2.ui.widgets.ErrorBox
import com.example.vpweek9.soal2.ui.widgets.LoadingBox
import com.example.vpweek9.soal2.ui.widgets.TrackItem
import com.example.vpweek9.soal2.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailScreen(
    onBackClick: () -> Unit,
    viewModel: AlbumDetailViewModel = viewModel(factory = AlbumDetailViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val album = uiState.album

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Detail Album", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        when {
            uiState.isLoading -> LoadingBox(message = "Memuat detail album & track list...")
            uiState.error != null -> ErrorBox(uiState.error.orEmpty(), onRetry = viewModel::load)
            album != null -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = album.coverUrl,
                            contentDescription = album.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(220.dp)
                                .clip(RoundedCornerShape(16.dp)),
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            album.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(album.artist, color = Color(0xFF1DB954), fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AssistChip(
                                onClick = {},
                                label = { Text("Tahun ${album.year.ifEmpty { "-" }}") })
                            AssistChip(onClick = {}, label = { Text(album.genre.ifEmpty { "-" }) })
                        }
                    }
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Deskripsi Album", fontWeight = FontWeight.Bold)
                        Text(
                            text = album.description.ifEmpty { "Deskripsi tidak tersedia." },
                            fontSize = 13.sp,
                            maxLines = if (expanded) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = if (expanded) "Tutup" else "Baca selengkapnya",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1DB954),
                            modifier = Modifier.clickable { expanded = !expanded },
                        )
                    }
                }
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Daftar Lagu", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "${uiState.tracks.size} Lagu",
                            color = Color(0xFF1DB954),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(uiState.tracks) { track ->
                    TrackItem(track = track)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumDetailScreenPreview() {
    AlbumDetailScreen(onBackClick = {})
}