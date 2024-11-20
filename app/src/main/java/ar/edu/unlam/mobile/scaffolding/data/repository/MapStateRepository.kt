package ar.edu.unlam.mobile.scaffolding.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MapStateRepository
    @Inject
    constructor() {
        // Flujo que contiene el estado del mapa: si se debe mostrar o no.
        private val _showMap = MutableStateFlow(false)
        val showMap: StateFlow<Boolean> get() = _showMap

        // Actualiza el estado para indicar que el mapa debe mostrarse o no.
        fun setShowMap(value: Boolean) {
            _showMap.value = value
        }

        // Resetea el estado del mapa para indicar que ya no debe mostrarse
        fun resetShowMap() {
            _showMap.value = false
        }
    }
