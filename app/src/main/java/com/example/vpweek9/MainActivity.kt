package com.example.vpweek9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vpweek9.soal1.ui.screen.PokemonScreen
import com.example.vpweek9.ui.theme.VPWEEK9Theme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.vpweek9.soal2.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VPWEEK9Theme(darkTheme = true, dynamicColor = false) {
               PokemonScreen() // Soal 1
                //AppNavHost() // Soal 2
            }
        }
    }
}