package ar.edu.unlam.mobile.scaffolding.domain.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.Location
import ar.edu.unlam.mobile.scaffolding.domain.models.RivalLocation

interface LocationRepository {
    suspend fun getLastKnownLocation(): Location?

    suspend fun getLastRivalLocation(): RivalLocation

    suspend fun updateRivalLocation(location: RivalLocation)
}
