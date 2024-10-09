package ar.edu.unlam.mobile.scaffolding.domain.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto

interface PictureRepository {
    suspend fun savePicture(picture: ByteArray)
    suspend fun getPicture(): UserPhoto
}
