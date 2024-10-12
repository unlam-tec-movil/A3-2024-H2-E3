package ar.edu.unlam.mobile.scaffolding.data.local

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.exceptions.CaptureUserPhotoExceptions
import ar.edu.unlam.mobile.scaffolding.data.repository.DataSource
import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import ar.edu.unlam.mobile.scaffolding.utils.otherwise
import ar.edu.unlam.mobile.scaffolding.utils.then
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class FileDataSource @Inject constructor(@ApplicationContext private val context: Context) :
    DataSource {
    companion object {
        private const val FILE_PATH = "user_photo.jpg"
    }

    private val pictureCached = mutableMapOf<String, ByteArray>()

    override suspend fun savePicture(picture: ByteArray) = try {
        context.filesDir.resolve(FILE_PATH).writeBytes(picture)
        pictureCached[FILE_PATH] = picture
    } catch (e: IOException) {
        throw CaptureUserPhotoExceptions.SavePictureException("Error while saving picture: ${e.message}")
    }

    override suspend fun getPicture(): ByteArray =
        try {
            pictureCached.isNotEmpty().then {
                pictureCached[FILE_PATH]
            }.otherwise {
                context.filesDir.resolve(FILE_PATH).readBytes()
            }
        } catch (e: IOException) {
            throw CaptureUserPhotoExceptions.SavePictureException("Error while reading picture file: ${e.message}")
        } catch (e: NoSuchFileException) {
            throw CaptureUserPhotoExceptions.PictureNotFoundException("Picture not found ${e.message}")
        }
}

