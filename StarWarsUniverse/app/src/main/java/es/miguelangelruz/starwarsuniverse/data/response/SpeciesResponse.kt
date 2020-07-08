package es.miguelangelruz.starwarsuniverse.data.response


import com.google.gson.annotations.SerializedName

data class SpeciesResponse(
    @SerializedName("average_height")
    val averageHeight: String,
    @SerializedName("average_lifespan")
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    @SerializedName("eye_colors")
    val eyeColors: String,
    val films: List<FilmResponse>,
    @SerializedName("hair_colors")
    val hairColors: String,
    val homeworld: String,
    val language: String,
    val name: String,
    val people: List<CharacterResponse>,
    @SerializedName("skin_colors")
    val skinColors: String,
    val url: String
)