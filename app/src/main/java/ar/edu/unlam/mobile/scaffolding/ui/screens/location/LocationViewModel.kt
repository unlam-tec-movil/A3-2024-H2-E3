package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.repository.LocationRepositoryImplementation
import ar.edu.unlam.mobile.scaffolding.data.repository.MapStateRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.Location
import ar.edu.unlam.mobile.scaffolding.domain.models.RivalLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
    @Inject
    constructor(
        // Inyección del repo que maneja la obtención de la ubicación
        private val locationRepositoryImplementation: LocationRepositoryImplementation,
        private val mapStateRepository: MapStateRepository,
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        // Estado del permiso, observable desde la UI
        private val _hasLocationPermission = MutableStateFlow(false)
        val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission
        val showMap = mapStateRepository.showMap
        private val _rivalLocation = MutableStateFlow(RivalLocation.FAR.location)
        val rivalLocation: StateFlow<Location> = _rivalLocation
        private val _userLocation = MutableStateFlow(Location(-34.668113504630966, -58.56664670589329))
        val userLocation: StateFlow<Location> = _userLocation
        private val _userImage: MutableStateFlow<ByteArray> = MutableStateFlow(byteArrayOf())
        val userImage: StateFlow<ByteArray> = _userImage

        init {
            checkLocationPermission()
            observeShowMap()
        }

        private fun observeShowMap() {
            viewModelScope.launch {
                mapStateRepository.showMap.collect { showMap ->
                    if (showMap) {
                        showLocation(showMap)
                        startMapTimer()
                    }
                }
            }
        }

        private fun startMapTimer() {
            viewModelScope.launch {
                delay(5000)
                mapStateRepository.resetShowMap()
            }
        }

        // Actualizamos el estado del permiso
        fun updateLocationPermissionStatus(isGranted: Boolean) {
            _hasLocationPermission.value = isGranted
        }

        // Metodo que verifica el estado alctual del permiso
        fun checkLocationPermission() {
            val hasCoarseLocationPermission =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED
            _hasLocationPermission.value = hasCoarseLocationPermission
        }

        // Obtenemos la ubicacion y motramos por ahora un toast para probar que funcione bien
        fun showLocation(showMap: Boolean) {
            viewModelScope.launch {
                // Verificamos permisos
                if (_hasLocationPermission.value && showMap) {
                    val locationResult = locationRepositoryImplementation.getLastKnownLocation()
                    if (locationResult != null) {
                        _userLocation.value = locationResult
                    } else {
                        Toast
                            .makeText(
                                context,
                                "No se pudo obtener la ubicación",
                                Toast.LENGTH_SHORT,
                            ).show()
                    }
                } else {
                    Toast
                        .makeText(
                            context,
                            "Permiso de ubicación no concedido",
                            Toast.LENGTH_SHORT,
                        ).show()
                }
            }
        }
    }
