package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.data.repository.MapStateRepository
import javax.inject.Inject

class ShowMapUseCase
    @Inject
    constructor(
        private val mapStateRepository: MapStateRepository,
    ) {
        operator fun invoke(showMap: Boolean) {
            // Actualiza el estado para mostrar el mapa
            mapStateRepository.setShowMap(showMap)
        }
    }
