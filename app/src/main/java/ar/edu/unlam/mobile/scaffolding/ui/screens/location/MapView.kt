package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.unlam.mobile.scaffolding.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    showMap: Boolean,
    userImage: ImageBitmap,
) {
    val mapViewModel: MapViewModel = viewModel()
    val currentContext = LocalContext.current

    // Player information
    val playerLatLng by mapViewModel.playerLatLng.collectAsState()
    val playerMarkerState = playerLatLng?.let { rememberMarkerState(position = it) }
    val userBitmap = userImage.asAndroidBitmap()
    val circularBitmap = getCircularBitmap(userBitmap, diameter = 200)
    val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(circularBitmap)

    // Attacker information
    val distance = 1500.0
    val bearing = 45.0
    val attackerLocation =
        playerLatLng?.let { mapViewModel.getDestinationLatLng(it, distance, bearing) }
    val attackerMarkerState =
        attackerLocation?.let { rememberMarkerState(position = attackerLocation) }
    val attackDrawable = ContextCompat.getDrawable(currentContext, R.drawable.payaso_marker)
    val attackIcon =
        attackDrawable
            ?.let {
                drawableToBitmap(it, it.intrinsicWidth, it.intrinsicHeight)
            }?.let { BitmapDescriptorFactory.fromBitmap(it) }
    val cameraPositionState =
        rememberCameraPositionState {
            position = playerLatLng?.let { CameraPosition.fromLatLngZoom(it, 13f) }!!
        }
    if (showMap) {
        GoogleMap(
            Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            if (playerMarkerState != null) {
                Marker(
                    state = playerMarkerState,
                    title = "Victima (Tú)",
                    snippet = "Vamos a atraparte",
                    icon = bitmapDescriptor,
                    onClick = { false },
                )
                if (attackerMarkerState != null && attackIcon != null) {
                    Marker(
                        state = attackerMarkerState,
                        title = "Crimpy",
                        snippet = "Aquí estoy!!!",
                        icon = attackIcon,
                        onClick = { false },
                    )
                }
                playerLatLng?.let {
                    Circle(
                        center = it,
                        radius = 3000.0,
                        strokeColor = Color.Red,
                        fillColor = Color(0x22FF0000),
                        strokeWidth = 2f,
                    )
                }
            }
            // Efecto lanzado para centrar y ajustar el zoom del mapa
            LaunchedEffect(cameraPositionState) {
                val cameraUpdate = playerLatLng?.let { CameraUpdateFactory.newLatLngZoom(it, 13f) }
                if (cameraUpdate != null) {
                    cameraPositionState.move(cameraUpdate)
                }
            }
        }
    }
}

fun drawableToBitmap(
    drawable: Drawable,
    intrinsicWidth: Int,
    intrinsicHeight: Int,
): Bitmap {
    val width = 200
    val height = 200
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun getCircularBitmap(
    bitmap: Bitmap,
    diameter: Int,
): Bitmap {
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, false)

    val output = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(output)

    val paint =
        Paint().apply {
            isAntiAlias = true
        }

    val path =
        android.graphics.Path().apply {
            addOval(
                RectF(0f, 0f, diameter.toFloat(), diameter.toFloat()),
                android.graphics.Path.Direction.CCW,
            )
        }

    canvas.clipPath(path)

    canvas.drawBitmap(resizedBitmap, 0f, 0f, paint)

    return output
}

/*AndroidView(
        modifier = modifier,
        factory = { context ->
            val map = MapView(context)
            map.setTileSource(TileSourceFactory.MAPNIK)
            map.setMultiTouchControls(false)
            map.controller.setZoom(15.5)
            return@AndroidView map
        },
        update = { mapView ->
            mapView.controller.setCenter(userLocation.toGeoPoint())
            val rivalMarker = Marker(mapView)
            val userMarker = Marker(mapView)
            rivalMarker.icon =
                ResourcesCompat.getDrawable(
                    currentContext.resources,
                    R.drawable.payaso_marker,
                    null,
                )
            userMarker.icon =
                getCircularBitmap(
                    userImage.asAndroidBitmap(),
                    200,
                ).toDrawable(resources = currentContext.resources)
            userMarker.position = rivalLocation.toGeoPoint()
            mapView.overlays.add(rivalMarker)
            mapView.overlays.add(userMarker)
            mapView.invalidate()
        },
    )*/
