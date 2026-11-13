package com.example.vpweek9.soal2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vpweek9.soal2.ui.screen.AlbumDetailScreen
import com.example.vpweek9.soal2.ui.screen.ArtistScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ArtistRoute) {
        composable<ArtistRoute> {
            ArtistScreen(
                onAlbumClick = { albumId -> navController.navigate(AlbumDetailRoute(albumId)) },
            )
        }
        composable<AlbumDetailRoute> {
            AlbumDetailScreen(
                onBackClick = { navController.navigateUp() },
            )
        }
    }
}