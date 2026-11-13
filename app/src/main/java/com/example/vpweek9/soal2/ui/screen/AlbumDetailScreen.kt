package com.example.vpweek9.soal2.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
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
import com.example.vpweek9.soal2.ui.widgets.CountBadge
import com.example.vpweek9.soal2.ui.widgets.ErrorBox
import com.example.vpweek9.soal2.ui.widgets.InfoPill
import com.example.vpweek9.soal2.ui.widgets.LoadingBox
import com.example.vpweek9.soal2.ui.widgets.MusicBg
import com.example.vpweek9.soal2.ui.widgets.MusicGreen
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

    Surface(Modifier.fillMaxSize(), color = MusicBg, contentColor = Color.White) {
        Column(
            Modifier
                .safeDrawingPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text("Detail Album", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            when {
                uiState.isLoading -> LoadingBox(message = "Memuat detail album & track list...")
                uiState.error != null -> ErrorBox(
                    uiState.error.orEmpty(),
                    onRetry = viewModel::load
                )

                album != null -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
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
                                    .size(250.dp)
                                    .clip(RoundedCornerShape(20.dp)),
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                album.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                album.artist,
                                color = MusicGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                InfoPill("Tahun ${album.year.ifEmpty { "-" }}")
                                InfoPill(album.genre.ifEmpty { "-" })
                            }
                        }
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Deskripsi Album", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(
                                text = album.description.ifEmpty { "Deskripsi tidak tersedia." },
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                color = Color(0xFFB4BAC3),
                                maxLines = if (expanded) Int.MAX_VALUE else 3,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = if (expanded) "Tutup ringkasan" else "Baca selengkapnya",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MusicGreen,
                                modifier = Modifier.clickable { expanded = !expanded },
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.MusicNote,
                                    contentDescription = null,
                                    tint = MusicGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text("Daftar Lagu", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                            CountBadge("${uiState.tracks.size} Lagu")
                        }
                    }
                    items(uiState.tracks) { track ->
                        TrackItem(track = track)
                    }
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