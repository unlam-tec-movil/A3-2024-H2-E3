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
        composable("hack_messages_screen") {
            HackMessagesScreen(navController = controller)
        }
        composable("captureUserPhoto") {
            CaptureUserScreen(
                navigateToGame = {
                    controller.navigate("menu_screen")
                },
            )
        }
        composable("game_screen") {
            GameScreen(navController = controller)
        }
        composable("menu_screen") {
            MenuScreen(
                onStartGameClick = {
                    controller.navigate("game_screen")
                },
            )
        }
        composable("location_screen") {
            LocationScreen(navController = controller)
        }
    }
}
