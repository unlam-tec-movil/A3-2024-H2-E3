package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CaptureUserFlowScreen(
    modifier: Modifier = Modifier,
    navigateToGame: () -> Unit,
) {
    var hasCameraPermission by remember { mutableStateOf(false) }

    if (!hasCameraPermission) {
        CameraPermissionRequester(
            onPermissionGranted = { hasCameraPermission = true },
            onPermissionDenied = { navigateToGame() },
        )
    } else {
        CaptureUserScreen(
            modifier = modifier,
            navigateToGame = navigateToGame,
        )
    }
}
