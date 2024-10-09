package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto

interface CaptureUserPhotoUseCases {
    suspend fun savePhoto(userPhoto: UserPhoto)
    suspend fun getPhoto(): UserPhoto?
}
