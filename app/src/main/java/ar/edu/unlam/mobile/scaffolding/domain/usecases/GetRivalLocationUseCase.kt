package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.RivalLocation

interface GetRivalLocationUseCase {
    fun getRivalLocation(): RivalLocation
}
