package ar.edu.unlam.mobile.scaffolding.domain.models

data class UserPhoto(val photo: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPhoto

        return photo.contentEquals(other.photo)
    }

    override fun hashCode(): Int {
        return photo.contentHashCode()
    }
}
