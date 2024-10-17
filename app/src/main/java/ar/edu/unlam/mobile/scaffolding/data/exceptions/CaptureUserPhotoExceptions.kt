package ar.edu.unlam.mobile.scaffolding.data.exceptions

import ar.edu.unlam.mobile.scaffolding.data.exceptions.CaptureUserPhotoExceptions.PictureNotFoundException
import ar.edu.unlam.mobile.scaffolding.data.exceptions.CaptureUserPhotoExceptions.SavePictureException
import ar.edu.unlam.mobile.scaffolding.data.exceptions.CaptureUserPhotoExceptions.UnknownError
import ar.edu.unlam.mobile.scaffolding.domain.models.errors.CaptureUserPhotoErrors

sealed class CaptureUserPhotoExceptions : Throwable() {
    data class UnknownError(
        override val message: String,
    ) : CaptureUserPhotoExceptions()

    data class SavePictureException(
        override val message: String,
    ) : CaptureUserPhotoExceptions()

    data class PictureNotFoundException(
        override val message: String,
    ) : CaptureUserPhotoExceptions()
}

fun CaptureUserPhotoExceptions.toError(): CaptureUserPhotoErrors =
    when (this) {
        is UnknownError -> CaptureUserPhotoErrors.UnknownError(message)
        is SavePictureException -> CaptureUserPhotoErrors.SavePictureError(message)
        is PictureNotFoundException -> CaptureUserPhotoErrors.PictureNotFound(message)
    }
