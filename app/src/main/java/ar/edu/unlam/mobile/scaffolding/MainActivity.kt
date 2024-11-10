package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.screens.AppNavHost
import ar.edu.unlam.mobile.scaffolding.ui.screens.CaptureUserScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.GameScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración para pantalla completa
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Ocultar barras
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        setContent {
            ScaffoldingV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = Routes.GAME_ROUTE) {
        composable(Routes.HOME_ROUTE) {
            HomeScreen(navController = controller)
        }
        composable(Routes.CAPTURE_USER_PHOTO_ROUTE) {
            CaptureUserScreen {
                controller.navigate("home")
            }
        }
        composable(Routes.GAME_ROUTE) {
            GameScreen(navController = controller)
        }
    }
}
