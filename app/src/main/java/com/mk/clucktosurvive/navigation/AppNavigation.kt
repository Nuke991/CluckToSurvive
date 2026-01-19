package com.mk.clucktosurvive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.clucktosurvive.ui.components.ReturnButton
import com.mk.clucktosurvive.ui.screens.*
import com.mk.clucktosurvive.ui.screens.GameOverScreen


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
                onStartClick = { navController.navigate("com/mk/clucktosurvive/ui/screens/game") },
                onRecordsClick = { navController.navigate("records") },
                onPrivacyClick = { navController.navigate("privacy") }
            )
        }


        composable("com/mk/clucktosurvive/ui/screens/game") {
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
                onRetry = {
                    navController.navigate("com/mk/clucktosurvive/ui/screens/game") {
                        popUpTo(
                            "com/mk/clucktosurvive/ui/screens/game"
                        ) { inclusive = true }
                    }
                },
                onBack = { navController.navigate("menu") { popUpTo("menu") { inclusive = true } } }
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