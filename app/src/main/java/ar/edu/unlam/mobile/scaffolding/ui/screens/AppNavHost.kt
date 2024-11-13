package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.screens.captureuserphoto.CaptureUserFlowScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.components.GameScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.hackmessage.HackMessagesScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.location.LocationScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.menu.MenuScreen
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.CAPTURE_USER_PHOTO_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.GAME_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.HACK_MESSAGE_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.MAP_ROUTE
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes.MENU_ROUTE

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
                    navController.navigate(MENU_ROUTE)
                },
            )
        }
        composable(MENU_ROUTE) {
            MenuScreen(
                onStartGameClick = {
                    navController.navigate(GAME_ROUTE)
                },
            )
        }
        composable(GAME_ROUTE) {
            GameScreen {
                navController.navigate(
                    it,
                    navOptions =
                        NavOptions
                            .Builder()
                            .setPopUpTo(
                                MENU_ROUTE,
                                inclusive = false,
                            ).build(),
                )
            }
        }
        composable(MAP_ROUTE) {
            LocationScreen(navController = navController)
        }
    }
}
