package com.example.vpweek9.soal1.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PokeBg = Color(0xFF11141A)
val PokeCard = Color(0xFF1B2029)
val PokeBorder = Color(0xFF2A303B)
val PokeRed = Color(0xFFE53935)
val PokeGreen = Color(0xFF2ECC71)
val PokeYellow = Color(0xFFF5C542)
val PokeMuted = Color(0xFF9AA3B2)

@Composable
fun PokeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PokeCard)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(PokeRed),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text("PokéTracker", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Real-time TCG Card Valuation", fontSize = 10.sp, color = PokeMuted)
        }
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(PokeGreen.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(PokeGreen)
            )
            Spacer(Modifier.width(4.dp))
            Text("LIVE API", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PokeGreen)
        }
    }
}

@Composable
fun LoadingBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PokeRed)
    }
}

@Composable
fun ErrorBox(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    val pink = Color(0xFFF2B8B5)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(PokeCard),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Filled.WifiOff,
                contentDescription = null,
                tint = pink,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Text("Connection Failed", color = pink, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(message, color = PokeMuted, fontSize = 12.sp, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PokeRed,
                contentColor = Color.White
            ),
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(6.dp))
            Text("Try Again", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}