package com.mk.clucktosurvive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mk.clucktosurvive.ui.screens.*


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "load") {

        composable("load") {
            LoadScreen(onTimeout = {
                navController.navigate("menu") { popUpTo("load") { inclusive = true } }
            })
        }

        composable("menu") {
            MenuScreen(
                onStartClick = { navController.navigate("game") },
                onRecordsClick = { navController.navigate("records") },
                onPrivacyClick = { navController.navigate("privacy") }
            )
        }


        composable("game") {
            GameScreen(
                onPauseClick = { navController.navigate("pause") },
                onGameOver = { navController.navigate("gameover") }
            )
        }


        composable("pause") {
            PauseScreen(
                onResume = { navController.popBackStack() },
                onExit = { navController.navigate("menu") { popUpTo("menu") { inclusive = true } } }
            )
        }


        composable("gameover") {
            GameOverScreen(
                onRetry = { navController.navigate("game") { popUpTo("game") { inclusive = true } } },
                onMenu = { navController.navigate("menu") { popUpTo("menu") { inclusive = true } } }
            )
        }


        composable("records") {
            RecordsScreen(onBack = {navController.navigate("menu")})

        }


        composable("privacy") {
            PrivacyScreen(
                onBack = {navController.navigate("menu")}
            )
        }
    }
}