package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import presentation.game.GameOverScreen
import presentation.game.GameScreen
import presentation.game.PauseScreen
import presentation.loading.LoadScreen
import presentation.menu.MenuScreen
import presentation.privacypolicy.PrivacyScreen
import presentation.records.RecordsScreen


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
            RecordsScreen(onBack = { navController.navigate("menu") })

        }


        composable("privacy") {
            PrivacyScreen(
                onBack = { navController.navigate("menu") }
            )
        }


    }
}