package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.Location
import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import ar.edu.unlam.mobile.scaffolding.ui.utils.toScaledBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    showMap: Boolean,
    playerLocation: Location,
    rivalLocation: Location,
    userPhoto: UserPhoto?,
) {
    val currentContext = LocalContext.current

    val playerMarkerState =
        playerLocation.let { rememberMarkerState(position = LatLng(it.latitude, it.longitude)) }
    val defaultPlayerDrawable = ContextCompat.getDrawable(currentContext, R.drawable.player_hanged)
    val playerIcon =
        userPhoto
            ?.photo
            .let {
                BitmapDescriptorFactory.fromBitmap(
                    it?.toScaledBitmap()
                        ?: drawableToBitmap(defaultPlayerDrawable!!),
                )
            }

    val attackerMarkerState =
        rivalLocation.let { rememberMarkerState(position = LatLng(it.latitude, it.longitude)) }
    val attackDrawable = ContextCompat.getDrawable(currentContext, R.drawable.payaso_marker)
    val attackIcon =
        attackDrawable
            ?.let {
                drawableToBitmap(it)
            }?.let { BitmapDescriptorFactory.fromBitmap(it) }
    val cameraPositionState =
        rememberCameraPositionState {
            CameraPosition.fromLatLngZoom(
                LatLng(playerLocation.latitude, playerLocation.longitude),
                13f,
            )
        }
    if (showMap) {
        GoogleMap(
            Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = playerMarkerState,
                title = "Victima (Tú)",
                snippet = "Vamos a atraparte",
                icon = playerIcon,
                onClick = { false },
            )
            if (attackIcon != null) {
                Marker(
                    state = attackerMarkerState,
                    title = "Crimpy",
                    snippet = "Aquí estoy!!!",
                    icon = attackIcon,
                    onClick = { false },
                )
            }
            playerLocation.let {
                Circle(
                    center = LatLng(it.latitude, it.longitude),
                    radius = 3000.0,
                    strokeColor = Color.Red,
                    fillColor = Color(0x22FF0000),
                    strokeWidth = 2f,
                )
            }
            LaunchedEffect(cameraPositionState) {
                val cameraUpdate =
                    playerLocation.let {
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it.latitude,
                                it.longitude,
                            ),
                            13f,
                        )
                    }
                cameraPositionState.move(cameraUpdate)
            }
        }
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    val width = 200
    val height = 200
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
