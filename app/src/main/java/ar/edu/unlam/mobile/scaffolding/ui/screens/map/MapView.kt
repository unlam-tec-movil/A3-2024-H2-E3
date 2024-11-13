package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import android.preference.PreferenceManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import ar.edu.unlam.mobile.scaffolding.R
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val currentContext = LocalContext.current
    getInstance().load(currentContext, PreferenceManager.getDefaultSharedPreferences(currentContext))
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context)
        },
        update = { mapView ->
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.controller.setZoom(15.5)
            val unlamPosition = GeoPoint(-34.667842278284176, -58.566202143018245)
            mapView.controller.setCenter(unlamPosition)
            val marker = Marker(mapView)
            marker.icon = ResourcesCompat.getDrawable(currentContext.resources, R.drawable.payaso_marker, null)
            marker.position = unlamPosition
            mapView.overlays.add(marker)
            mapView.invalidate()
        },
    )
}
