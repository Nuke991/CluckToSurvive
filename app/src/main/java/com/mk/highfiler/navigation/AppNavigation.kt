package com.mk.highfiler.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.highfiler.presentation.game.GameScreen
import com.mk.highfiler.presentation.loading.LoadScreen
import com.mk.highfiler.presentation.menu.MenuScreen
import com.mk.highfiler.presentation.privacypolicy.PrivacyScreen
import com.mk.highfiler.presentation.records.RecordsScreen
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController


fun NavController.navigateSingle(route: String){
    if(currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED){
        navigate(route)
    }
}


@Composable
fun AppNavigation() {

    val navController = rememberNavController()




    NavHost(navController = navController, startDestination = "load") {

        composable("load") {
            LoadScreen(onTimeout = {
                navController.navigateSingle("menu")
            })
        }

        composable("menu") {
            MenuScreen(
                onStartClick = { navController.navigateSingle("presentation/game") },
                onRecordsClick = { navController.navigateSingle("records") },
                onPrivacyClick = { navController.navigateSingle("privacy") }
            )
        }


        composable("presentation/game") {
            GameScreen(

                onExit = {
                    navController.navigateSingle("menu")
                },
                onPlayAgain = {
                    navController.navigateSingle("presentation/game")
                }
            )
        }



        composable("records") {
            RecordsScreen(
                onBack = { navController.navigateSingle("menu") }
            )

        }


        composable("privacy") {
            PrivacyScreen(
                onBack = { navController.navigateSingle("menu") }
            )
        }


    }
}