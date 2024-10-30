package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.repository.LocationRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
    @Inject
    constructor(
        // Inyección del repo que maneja la obtención de la ubicación
        private val locationRepositoryImplementation: LocationRepositoryImplementation,
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        // Estado del permiso, observable desde la UI
        private val _hasLocationPermission = MutableStateFlow(false)
        val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission

        init {
            checkLocationPermission()
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
        fun showLocation(context: Context) {
            viewModelScope.launch {
                // Verificamos permisos
                if (_hasLocationPermission.value) {
                    val locationResult = locationRepositoryImplementation.getLastKnownLocation()

                    // todo Modoficar luego, por ahora mostramos las coordenadas en un toast
                    if (locationResult != null) {
                        Toast.makeText(
                            context,
                            "Longitud = ${locationResult.longitude}, Longitud: ${locationResult.latitude}",
                            Toast.LENGTH_LONG,
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "No se pudo obtener la ubicación",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Permiso de ubicación no concedido",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
