package ar.edu.unlam.mobile.scaffolding.data.repository

interface DataSource {
    suspend fun savePicture(picture: ByteArray)

    suspend fun getPicture(): ByteArray
}
