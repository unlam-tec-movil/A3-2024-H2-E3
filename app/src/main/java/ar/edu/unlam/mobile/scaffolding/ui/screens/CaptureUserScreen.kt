package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffolding.ui.utils.toByteArray
import ar.edu.unlam.mobile.scaffolding.ui.utils.toImageBitmap
import kotlinx.coroutines.delay

@Composable
fun CaptureUserScreen(
    modifier: Modifier = Modifier,
    viewModel: CaptureUserViewModel = hiltViewModel(),
    navigateToGame: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val captureUserPhoto =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { picture ->
            viewModel.onCapturedUserPhoto(picture?.toByteArray())
        }
    when (val state = uiState) {
        is CaptureUserPhotoUiState.Starting -> {
            LaunchedEffect(key1 = true) {
                captureUserPhoto.launch()
            }
        }

        is CaptureUserPhotoUiState.PhotoTaken -> {
            Image(
                modifier = modifier.fillMaxSize(),
                bitmap = state.userPhoto.photo.toImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            LaunchedEffect(key1 = true) {
                delay(2000) // Temporary
                navigateToGame()
            }
            // Play animation to next screen
        }

        is CaptureUserPhotoUiState.Error -> {
            navigateToGame()
        }
    }
}
