package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val repository: LocationRepository,
    ) : ViewModel() {
        private val _playerLatLng = MutableStateFlow<LatLng?>(null)
        val playerLatLng: StateFlow<LatLng?> = _playerLatLng.asStateFlow()

        fun getPlayerLatLng() {
            viewModelScope.launch {
                val location = repository.getLastKnownLocation() // Devuelve ResultLocation
                location?.let {
                    _playerLatLng.value = LatLng(it.latitude, it.longitude)
                }
            }
        }

        fun getDestinationLatLng(
            startLatLng: LatLng,
            distanceInMeters: Double,
            bearing: Double,
        ): LatLng {
            val radio = 6371000.0 // Radio de la Tierra en metros
            val lat1 = Math.toRadians(startLatLng.latitude)
            val lng1 = Math.toRadians(startLatLng.longitude)
            val bearingRad = Math.toRadians(bearing)
            val lat2 =
                Math.asin(
                    Math.sin(lat1) *
                        Math.cos(
                            distanceInMeters /
                                radio,
                        ) + Math.cos(lat1) * Math.sin(distanceInMeters / radio) *
                        Math.cos(
                            bearingRad,
                        ),
                )
            val lng2 =
                lng1 +
                    Math.atan2(
                        Math.sin(bearingRad) *
                            Math.sin(
                                distanceInMeters /
                                    radio,
                            ) * Math.cos(lat1),
                        Math.cos(distanceInMeters / radio) - Math.sin(lat1) * Math.sin(lat2),
                    )
            return LatLng(Math.toDegrees(lat2), Math.toDegrees(lng2))
        }
    }
