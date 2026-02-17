package com.mk.clucktosurvive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.clucktosurvive.presentation.game.GameOverScreen
import com.mk.clucktosurvive.presentation.game.GameScreen
import com.mk.clucktosurvive.presentation.loading.LoadScreen
import com.mk.clucktosurvive.presentation.menu.MenuScreen
import com.mk.clucktosurvive.presentation.privacypolicy.PrivacyScreen
import com.mk.clucktosurvive.presentation.records.RecordsScreen


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
                onStartClick = { navController.navigate("presentation/game") },
                onRecordsClick = { navController.navigate("records") },
                onPrivacyClick = { navController.navigate("privacy") }
            )
        }


        composable("presentation/game") {
            GameScreen(
                // onGameOver = { navController.navigate("gameover") },
                onExit = {
                    navController.navigate("menu") {
                        popUpTo("menu") { inclusive = true }
                    }
                },
                onPlayAgain = {
                    navController.navigate("presentation/game") {
                        popUpTo(
                            "presentation/game"
                        ) { inclusive = true }
                    }
                }
            )
        }



/*
        composable("gameover") {
            GameOverScreen(
                onPlayAgain = {
                    navController.navigate("presentation/game") {
                        popUpTo(
                            "presentation/game"
                        ) { inclusive = true }
                    }
                },
                onBack = { navController.navigate("menu") { popUpTo("menu") { inclusive = true } } }
            )
        }
*/

        composable("records") {
            RecordsScreen(

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