package ar.edu.unlam.mobile.scaffolding.ui.screens.captureuserphoto

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun CameraPermissionRequester(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val requestCameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }

    LaunchedEffect(Unit) {
        requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
}

/*/ Usamos el estado del ViewModel para la navegacion
if (cameraPermissionViewModel.navigateToCaptureScreen.value) {
    navController.navigate("captureUserPhoto")
} else if (cameraPermissionViewModel.navigateToGameScreen.value) {
    navController.navigate("game_screen")
}*/
