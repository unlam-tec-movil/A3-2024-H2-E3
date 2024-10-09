package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.domain.usecases.CaptureUserPhotoUseCases
import ar.edu.unlam.mobile.scaffolding.domain.repository.PictureRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import javax.inject.Inject

class CaptureUserPhotoService @Inject constructor(private val picturesRepository: PictureRepository) :
    CaptureUserPhotoUseCases {
    override suspend fun savePhoto(userPhoto: UserPhoto) {
        picturesRepository.savePicture(userPhoto.photo)
    }

    override suspend fun getPhoto(): UserPhoto? {
        return picturesRepository.getPicture()
    }
}

