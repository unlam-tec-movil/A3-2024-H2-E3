package ar.edu.unlam.mobile.scaffolding.data.di

import ar.edu.unlam.mobile.scaffolding.data.repository.MapStateRepository
import ar.edu.unlam.mobile.scaffolding.domain.usecases.ShowMapUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMapStateRepository(mapStateRepository: MapStateRepository): ShowMapUseCase = ShowMapUseCase(mapStateRepository)
}
