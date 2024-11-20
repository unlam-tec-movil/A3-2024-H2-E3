package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLocationEnabled != null && uiState.isLocationEnabled!! && uiState.userLocation != null) {
        // Muestra la UI que requiere permiso de ubicación
        MapScreen(
            modifier = Modifier.padding(16.dp),
            showMap = uiState.isMapVisible,
            playerLocation = uiState.userLocation!!,
            rivalLocation = uiState.rivalLocation.location,
            userPhoto = uiState.userPhoto,
        )
    } else {
        RequestLocationPermission(
            hasPermission = uiState.isLocationEnabled,
        ) {
            viewModel.checkLocationPermission()
        }
    }
    if (uiState.backToGame) {
        navController.navigate(Routes.GAME_ROUTE)
    }
}

// Solicitud de permiso y actualización del estado
@Composable
fun RequestLocationPermission(
    hasPermission: Boolean?,
    onPermissionResponse: (Boolean) -> Unit,
) {
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            onPermissionResponse(isGranted)
        }

    LaunchedEffect(hasPermission) {
        if (hasPermission != null && !hasPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
}
