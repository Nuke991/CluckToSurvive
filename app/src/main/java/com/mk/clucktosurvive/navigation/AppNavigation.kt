package com.mk.clucktosurvive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.clucktosurvive.ui.screens.*




@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
  composable("splash") {
      SplashScreen(onTimeout = { navController.navigate("menu") })
  }
  composable("menu"){
      MenuScreen()
  }

 }
}