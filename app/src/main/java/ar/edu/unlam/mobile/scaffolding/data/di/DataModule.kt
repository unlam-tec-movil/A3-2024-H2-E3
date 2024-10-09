package ar.edu.unlam.mobile.scaffolding.data.di

import ar.edu.unlam.mobile.scaffolding.data.local.FileDataSource
import ar.edu.unlam.mobile.scaffolding.data.repository.DataSource
import ar.edu.unlam.mobile.scaffolding.data.repository.PictureRepositoryService
import ar.edu.unlam.mobile.scaffolding.domain.repository.PictureRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindDataSource(dataSource: FileDataSource): DataSource

    @Binds
    @Singleton
    abstract fun bindPictureRepositoryService(pictureRepositoryService: PictureRepositoryService): PictureRepository

}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}