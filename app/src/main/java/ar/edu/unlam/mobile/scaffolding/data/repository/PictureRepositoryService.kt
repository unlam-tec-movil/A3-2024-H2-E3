package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import ar.edu.unlam.mobile.scaffolding.domain.repository.PictureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PictureRepositoryService
    @Inject
    constructor(
        private val localDataSource: DataSource,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : PictureRepository {
        override suspend fun savePicture(picture: ByteArray) =
            withContext(coroutineDispatcher) {
                localDataSource.savePicture(picture)
            }

        override suspend fun getPicture(): UserPhoto =
            withContext(coroutineDispatcher) {
                UserPhoto(localDataSource.getPicture())
            }
    }
