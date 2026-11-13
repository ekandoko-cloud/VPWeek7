package com.example.vpweek9.soal2.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.vpweek9.soal2.model.Album
import com.example.vpweek9.soal2.model.Artist
import com.example.vpweek9.soal2.model.Track

@Composable
fun ArtistInfo(artist: Artist, expanded: Boolean, onToggle: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            AsyncImage(
                model = artist.bannerUrl,
                contentDescription = artist.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(0.35f to Color.Transparent, 1f to MusicBg),
                    ),
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = artist.thumbUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        artist.name,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (artist.genre.isNotEmpty()) {
                            Text(
                                artist.genre,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MusicGreen
                            )
                        }
                        if (artist.genre.isNotEmpty() && artist.country.isNotEmpty()) {
                            Text("  •  ", fontSize = 11.sp, color = MusicMuted)
                        }
                        if (artist.country.isNotEmpty()) {
                            Text(artist.country, fontSize = 11.sp, color = MusicMuted)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(4.dp))
        Text("Tentang Artis", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Text(
            text = artist.biography.ifEmpty { "Biografi tidak tersedia." },
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = Color(0xFFB4BAC3),
            maxLines = if (expanded) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = if (expanded) "Tutup ringkasan" else "Baca selengkapnya",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MusicGreen,
            modifier = Modifier.clickable { onToggle() },
        )
    }
}

@Composable
fun AlbumCard(album: Album, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(
            model = album.coverUrl,
            contentDescription = album.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
        )
        Spacer(Modifier.height(2.dp))
        Text(
            album.title,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(album.year, fontSize = 11.sp, color = MusicGreen, fontWeight = FontWeight.Bold)
            Text(album.genre, fontSize = 11.sp, color = MusicMuted, maxLines = 1)
        }
    }
}

@Composable
fun InfoPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MusicSurface)
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(text, fontSize = 11.sp, color = Color(0xFFB4BAC3))
    }
}

@Composable
fun TrackItem(track: Track, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                track.number,
                color = MusicGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.width(32.dp)
            )
            Column(Modifier.weight(1f)) {
                Text(track.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(track.genre, fontSize = 11.sp, color = MusicMuted)
            }
            Icon(
                Icons.Filled.Schedule,
                contentDescription = null,
                tint = MusicMuted,
                modifier = Modifier.size(12.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(track.duration, fontSize = 12.sp, color = MusicMuted)
        }
        HorizontalDivider(color = MusicBorder)
    }
}