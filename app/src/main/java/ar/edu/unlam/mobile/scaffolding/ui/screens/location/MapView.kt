package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.Location
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    showMap: Boolean,
    rivalLocation: Location,
    userLocation: Location,
    userImage: ImageBitmap,
) {
    val currentContext = LocalContext.current
    // getInstance().load(currentContext, PreferenceManager.getDefaultSharedPreferences(currentContext))
    AndroidView(
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
    )
}

fun Location.toGeoPoint(): GeoPoint = GeoPoint(latitude, longitude)

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
