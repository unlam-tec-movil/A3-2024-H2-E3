package ar.edu.unlam.mobile.scaffolding.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ar.edu.unlam.mobile.scaffolding.domain.models.Location
import ar.edu.unlam.mobile.scaffolding.domain.models.RivalLocation
import ar.edu.unlam.mobile.scaffolding.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImplementation
    @Inject
    constructor(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        @ApplicationContext private val context: Context,
    ) : LocationRepository {
        private var rivalLocationStorage = RivalLocation.FAR

        override suspend fun getLastKnownLocation(): Location? {
            // Verificamos permisos
            val hasPermission =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED

            // Sin permisos retornamos null
            if (!hasPermission) return null

            return suspendCancellableCoroutine { continuation ->
                // Intentamos obtener la última ubacación conocida
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location ->
                        // Si obtenemos una ubicación válida, retomamos la coroutine con la ubicación
                        if (location != null) {
                            continuation.resume((Location(location.latitude, location.longitude)))
                        } else {
                            // Si la ubicacion no esta disponible, retomamos la coroutine con null
                            continuation.resume(null)
                        }
                    }.addOnFailureListener {
                        // En caso de error, retomamos la coroutine con null
                        continuation.resume(null)
                    }
            }
        }

        override suspend fun getLastRivalLocation(): RivalLocation = rivalLocationStorage

        override suspend fun updateRivalLocation(location: RivalLocation) {
            this.rivalLocationStorage = location
        }
    }

@Module
@InstallIn(SingletonComponent::class)
object FusedLocationProviderModule {
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context,
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
}
