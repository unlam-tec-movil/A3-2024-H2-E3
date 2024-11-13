package ar.edu.unlam.mobile.scaffolding.domain.models

enum class RivalLocation(
    val location: Location,
) {
    FAR(Location(-34.66636236696946, -58.52981596135305)),
    CLOSE(Location(-34.67711145837357, -58.562528320718016)),
    ON_SAME_LOCATION(Location(-34.66942481987034, -58.55979720502083)),
}

data class Location(
    val latitude: Double,
    val longitude: Double,
)
