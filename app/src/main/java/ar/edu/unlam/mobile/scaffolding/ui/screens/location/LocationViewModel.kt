package ar.edu.unlam.mobile.scaffolding.ui.screens.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.repository.LocationRepositoryImplementation
import ar.edu.unlam.mobile.scaffolding.domain.models.Location
import ar.edu.unlam.mobile.scaffolding.domain.models.RivalLocation
import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import ar.edu.unlam.mobile.scaffolding.domain.usecases.CaptureUserPhotoUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetRivalLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
    @Inject
    constructor(
        // Inyección del repo que maneja la obtención de la ubicación
        private val repository: LocationRepositoryImplementation,
        private val getUserPhotoUseCases: CaptureUserPhotoUseCases,
        private val getRivalLocationUseCase: GetRivalLocationUseCase,
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MapScreenState())
        val uiState: StateFlow<MapScreenState> = _uiState.asStateFlow()

        private fun getPlayerLocation() {
            viewModelScope.launch {
                val location = repository.getLastKnownLocation()
                val userPhoto = getUserPhotoUseCases.getPhoto() // Devuelve ResultLocation
                _uiState.update { currentState ->
                    currentState.copy(
                        userLocation = Location(location?.latitude ?: 0.0, location?.longitude ?: 0.0),
                        isMapVisible = true,
                        userPhoto = userPhoto,
                    )
                }
                startMapTimer()
            }
        }

        init {
            checkLocationPermission()
        }

        private fun startMapTimer() {
            viewModelScope.launch {
                delay(8000)
                _uiState.update { it.copy(backToGame = true) }
            }
        }

        // Metodo que verifica el estado alctual del permiso
        fun checkLocationPermission() {
            val hasCoarseLocationPermission =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED
            _uiState.update { it.copy(isLocationEnabled = hasCoarseLocationPermission) }
            if (hasCoarseLocationPermission) {
                getPlayerLocation()
            }
        }
    }

data class MapScreenState(
    val isLocationEnabled: Boolean? = null,
    val isMapVisible: Boolean = false,
    val userLocation: Location? = null,
    val rivalLocation: RivalLocation = RivalLocation.FAR,
    val userPhoto: UserPhoto? = null,
    val backToGame: Boolean = false,
)
