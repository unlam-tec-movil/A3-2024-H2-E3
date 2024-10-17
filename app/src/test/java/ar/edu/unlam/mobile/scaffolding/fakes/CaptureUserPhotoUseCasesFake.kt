package ar.edu.unlam.mobile.scaffolding.fakes

import ar.edu.unlam.mobile.scaffolding.data.exceptions.CaptureUserPhotoExceptions
import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import ar.edu.unlam.mobile.scaffolding.domain.usecases.CaptureUserPhotoUseCases

class CaptureUserPhotoUseCasesFake : CaptureUserPhotoUseCases {
    private var userPhoto: UserPhoto? = null

    override suspend fun savePhoto(userPhoto: UserPhoto) {
        if (userPhoto.photo.isEmpty()) throw CaptureUserPhotoExceptions.SavePictureException("Error while saving the picture")
        this.userPhoto = userPhoto
    }

    override suspend fun getPhoto(): UserPhoto? = userPhoto
}
