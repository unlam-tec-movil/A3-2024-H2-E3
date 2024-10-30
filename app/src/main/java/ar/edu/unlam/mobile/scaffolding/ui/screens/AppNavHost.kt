package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = "hack_messages_screen",
    ) {
        composable("home") {
            HomeScreen(navController = controller)
        }
        composable("hack_messages_screen") {
            HackMessagesScreen(navController = controller)
        }
        composable("captureUserPhoto") {
            CaptureUserScreen(
                navigateToGame = {
                    controller.navigate("home")
                },
            )
        }
        composable("game_screen") {
            GameScreen(navController = controller)
        }
        composable("location_screen") {
            LocationScreen(navController = controller)
        }
    }
}
