package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.Location

interface GetRivalLocationUseCase {
    suspend fun getRivalLocation(isWin: Boolean): Location
}
