package ar.edu.unlam.mobile.scaffolding.domain.models.errors

sealed class CaptureUserPhotoErrors(open val message: String = "Unknown error") {
    data class UnknownError(override val message: String) : CaptureUserPhotoErrors(message)
    data class SavePictureError(override val message: String) : CaptureUserPhotoErrors(message)
    data class PictureNotFound(override val message: String) : CaptureUserPhotoErrors(message)
}