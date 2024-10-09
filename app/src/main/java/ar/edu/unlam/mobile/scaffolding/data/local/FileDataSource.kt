package ar.edu.unlam.mobile.scaffolding.data.local

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.exceptions.CaptureUserPhotoExceptions
import ar.edu.unlam.mobile.scaffolding.data.repository.DataSource
import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class FileDataSource @Inject constructor(@ApplicationContext private val context: Context) :
    DataSource {
    companion object {
        private const val FILE_PATH = "user_photo.jpg"
    }

    override suspend fun savePicture(picture: ByteArray) = try {
        context.filesDir.resolve(FILE_PATH).writeBytes(picture)
    } catch (e: IOException) {
        throw CaptureUserPhotoExceptions.SavePictureException("Error while saving picture: ${e.message}")
    }

    override suspend fun getPicture(): UserPhoto =
        try {
            context.filesDir.resolve(FILE_PATH).readBytes().let {
                return@let UserPhoto(it)
            }
        } catch (e: IOException) {
            throw CaptureUserPhotoExceptions.SavePictureException("Error while reading picture file: ${e.message}")
        } catch (e: NoSuchFileException) {
            throw CaptureUserPhotoExceptions.PictureNotFoundException("Picture not found ${e.message}")
        }
}