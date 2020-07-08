package es.miguelangelruz.starwarsuniverse.data.response


import com.google.gson.annotations.SerializedName

data class PlanetResponse(
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: List<FilmResponse>,
    val gravity: String,
    val name: String,
    @SerializedName("orbital_period")
    val orbitalPeriod: String,
    val population: String,
    val residents: List<CharacterResponse>,
    @SerializedName("rotation_period")
    val rotationPeriod: String,
    @SerializedName("surface_water")
    val surfaceWater: String,
    val terrain: String,
    val url: String
)