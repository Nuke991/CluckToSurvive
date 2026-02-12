package com.mk.clucktosurvive.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.clucktosurvive.presentation.game.GameOverScreen
import com.mk.clucktosurvive.presentation.game.GameScreen
import com.mk.clucktosurvive.presentation.game.GameViewModel
import com.mk.clucktosurvive.presentation.game.PauseScreen
import com.mk.clucktosurvive.presentation.loading.LoadScreen
import com.mk.clucktosurvive.presentation.menu.MenuScreen
import com.mk.clucktosurvive.presentation.privacypolicy.PrivacyScreen
import com.mk.clucktosurvive.presentation.records.RecordsScreen
import com.mk.clucktosurvive.presentation.records.RecordsScreenViewModel

import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val gameViewModel: GameViewModel = koinViewModel()
    val recordsScreenViewModel: RecordsScreenViewModel = koinViewModel()


    NavHost(navController = navController, startDestination = "load") {

        composable("load") {
            LoadScreen(onTimeout = {
                navController.navigate("menu") { popUpTo("load") { inclusive = true } }
            })
        }

        composable("menu") {
            MenuScreen(
                onStartClick = { navController.navigate("presentation/game") },
                onRecordsClick = { navController.navigate("records") },
                onPrivacyClick = { navController.navigate("privacy") }
            )
        }


        composable("presentation/game") {
            GameScreen(
                gameViewModel,
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
                    navController.navigate("presentation/game") {
                        popUpTo(
                            "presentation/game"
                        ) { inclusive = true }
                    }
                },
                onBack = { navController.navigate("menu") { popUpTo("menu") { inclusive = true } } }
            )
        }


        composable("records") {
            RecordsScreen(
                recordsScreenViewModel,
                onBack = { navController.navigate("menu") }

            )

        }


        composable("privacy") {
            PrivacyScreen(
                onBack = { navController.navigate("menu") }
            )
        }


    }
}