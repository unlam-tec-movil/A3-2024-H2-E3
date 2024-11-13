package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "hack_messages_screen",
        // startDestination = "captureUserPhoto" para chequear más rápido la lógica
    ) {
        composable("hack_messages_screen") {
            HackMessagesScreen(
                navController = navController,
            )
        }
        composable("captureUserPhoto") {
            CaptureUserFlowScreen(
                navigateToGame = {
                    navController.navigate("CaptureFlow")
                },
            )
        }
        composable("game_screen") {
            GameScreen(navController = navController)
        }
        composable("menu_screen") {
            MenuScreen(
                onStartGameClick = {
                    navController.navigate("game_screen")
                },
            )
        }
        composable("location_screen") {
            LocationScreen(navController = navController)
        }
    }
}
