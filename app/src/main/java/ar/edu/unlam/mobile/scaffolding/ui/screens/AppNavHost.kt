package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.screens.captureuserphoto.CaptureUserFlowScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.components.GameScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.hackmessage.HackMessagesScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.location.LocationScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.losegame.EndScreen
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.CAPTURE_USER_PHOTO_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.GAME_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.HACK_MESSAGE_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.LOSE_GAME
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.MAP_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.WIN_GAME
import kotlinx.coroutines.delay

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HACK_MESSAGE_ROUTE,
    ) {
        composable(HACK_MESSAGE_ROUTE) {
            HackMessagesScreen(
                navController = navController,
            )
        }
        composable(CAPTURE_USER_PHOTO_ROUTE) {
            CaptureUserFlowScreen(
                navigateToMenu = {
                    navController.navigate(GAME_ROUTE)
                },
            )
        }
        composable(GAME_ROUTE) {
            GameScreen {
                navController.navigate(
                    it,
                )
            }
        }
        composable(MAP_ROUTE) {
            LocationScreen(navController = navController)
        }
        composable(LOSE_GAME) {
            EndScreen(
                modifier = Modifier.fillMaxSize(),
                text = "Perdiste, te atrapamos para siempre",
            )
        }
        composable(WIN_GAME) {
            EndScreen(
                modifier = Modifier.fillMaxSize(),
                text = "Ganaste esta vez, pero la proxima te alcanzare",
            )
            LaunchedEffect(Unit) {
                delay(7000)
                throw Exception("Game Over")
            }
        }
    }
}
