package ar.edu.unlam.mobile.scaffolding.domain.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.LocationResult

interface LocationRepository {
    suspend fun getLastKnownLocation(): LocationResult?
}