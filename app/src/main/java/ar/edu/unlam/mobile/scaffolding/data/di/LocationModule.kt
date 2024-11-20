package ar.edu.unlam.mobile.scaffolding.data.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.repository.LocationRepositoryImplementation
import ar.edu.unlam.mobile.scaffolding.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient,
        @ApplicationContext context: Context,
    ): LocationRepository = LocationRepositoryImplementation(fusedLocationProviderClient, context)
}
