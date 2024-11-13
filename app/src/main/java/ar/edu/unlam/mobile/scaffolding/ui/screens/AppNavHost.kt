package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.components.GameScreenPreview
import ar.edu.unlam.mobile.scaffolding.ui.screens.map.MapScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "hack_messages_screen",
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
            var triggerAnim by remember {
                mutableStateOf(false)
            }
            GameScreenPreview(triggerAnim = triggerAnim, onDrawCard = {
                triggerAnim = true
            })
            // GameScreen(navController = navController)
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
        composable("map_screen") {
            MapScreen(modifier = Modifier.fillMaxSize())
        }
    }
}
