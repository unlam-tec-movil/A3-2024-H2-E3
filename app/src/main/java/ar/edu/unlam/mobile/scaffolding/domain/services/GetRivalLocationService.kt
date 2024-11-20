package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.domain.models.Location
import ar.edu.unlam.mobile.scaffolding.domain.repository.LocationRepository
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetRivalLocationUseCase
import javax.inject.Inject
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class GetRivalLocationService
    @Inject
    constructor(
        private val locationRepository: LocationRepository,
    ) : GetRivalLocationUseCase {
        private var lastLocation: Location? = null

        override suspend fun getRivalLocation(isWin: Boolean): Location {
            locationRepository.getLastKnownLocation()?.let {
                if (lastLocation == null) {
                    getRandomLocationWithinRadius(it.latitude, it.longitude, 3000.0).let { (lat, lon) ->
                        lastLocation = Location(lat, lon)
                    }
                } else {
                    if (!isWin)
                        {
                        }
                }
            } ?: run {
                lastLocation = Location(0.0, 0.0)
            }
            return lastLocation!!
        }

        private fun getRandomLocationWithinRadius(
            centerLat: Double,
            centerLon: Double,
            radiusKm: Double,
        ): Pair<Double, Double> {
            val earthRadiusKm = 6371.0 // Earth's radius in kilometers

            val randomDistance = radiusKm * sqrt(Math.random()) // sqrt for uniform distribution
            val randomAngle = Math.random() * 2 * Math.PI

            val distanceRadians = randomDistance / earthRadiusKm

            val randomLat =
                asin(
                    sin(Math.toRadians(centerLat)) * cos(distanceRadians) +
                        cos(Math.toRadians(centerLat)) * sin(distanceRadians) * cos(randomAngle),
                )

            val randomLon =
                Math.toRadians(centerLon) +
                    atan2(
                        sin(randomAngle) * sin(distanceRadians) * cos(Math.toRadians(centerLat)),
                        cos(distanceRadians) - sin(Math.toRadians(centerLat)) * sin(randomLat),
                    )

            return Pair(Math.toDegrees(randomLat), Math.toDegrees(randomLon))
        }
    }
