package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.domain.models.RivalLocation
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetRivalLocationUseCase
import javax.inject.Inject

class GetRivalLocationService
    @Inject
    constructor() : GetRivalLocationUseCase {
        private var isWin = false

        override fun getRivalLocation(): RivalLocation = RivalLocation.FAR

        // private fun calculateRivalDistance(): Double {}
    }
