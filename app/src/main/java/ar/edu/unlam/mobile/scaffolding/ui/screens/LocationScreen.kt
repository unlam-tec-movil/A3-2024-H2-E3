package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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

    // Verificamos el permiso inicial al principio de la función
    LaunchedEffect(Unit) {
        viewModel.checkLocationPermission()
    }

    if (hasPermission) {
        // Muestra la UI que requiere permiso de ubicación
        Text("Se puede acceder a la ubicacion")
        Spacer(modifier = Modifier.padding(50.dp))
        Button(
            onClick = {
                // Mostramos la ubicacion en latitud y longitud
                viewModel.showLocation(context)
            },
        ) {
            Text("Obtener ubicación")
        }
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
