package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val hasPermission by viewModel.hasLocationPermission.collectAsState()
    val showMap by viewModel.showMap.collectAsState()
    val userImage by viewModel.userImage.collectAsState()
    // Verificamos el permiso inicial al principio de la función
    LaunchedEffect(Unit) {
        viewModel.checkLocationPermission()
    }

    if (hasPermission) {
        // Muestra la UI que requiere permiso de ubicación
        MapScreen(
            modifier = Modifier.padding(16.dp),
            showMap = showMap,
            // userImage = userImage.toImageBitmap(),
        )
    } else {
        // Llama al composable para solicitar permiso
        Text("Se volvera a pedir permiso")
        RequestLocationPermission(
            viewModel = viewModel,
        )
    }
}

// Solicitud de permiso y actualización del estado
@Composable
fun RequestLocationPermission(viewModel: LocationViewModel) {
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            viewModel.updateLocationPermissionStatus(isGranted)
        }

    // Obtenemos el estado de permiso actual
    val hasPermission by viewModel.hasLocationPermission.collectAsState()

    // Solicitar el permiso cuando no está concedido
    LaunchedEffect(hasPermission) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
}
