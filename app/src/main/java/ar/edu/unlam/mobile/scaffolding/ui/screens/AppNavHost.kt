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
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = "map_screen",
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
            // GameScreen(navController = controller)
            var triggerAnim by remember {
                mutableStateOf(false)
            }
            GameScreenPreview(triggerAnim = triggerAnim, onDrawCard = {
                triggerAnim = true
            })
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
        composable("map_screen") {
            MapScreen(modifier = Modifier.fillMaxSize())
        }
    }
}
