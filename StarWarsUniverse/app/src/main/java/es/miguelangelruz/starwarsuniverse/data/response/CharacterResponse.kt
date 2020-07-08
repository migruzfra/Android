package es.miguelangelruz.starwarsuniverse.data.response


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("birth_year")
    val birthYear: String,
    val created: String,
    val edited: String,
    @SerializedName("eye_color")
    val eyeColor: String,
    val films: List<String>,
    val gender: String,
    @SerializedName("hair_color")
    val hairColor: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    @SerializedName("skin_color")
    val skinColor: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
)

//data class CharacterResponse(
//    @SerializedName("birth_year")
//    val birthYear: String,
//    val created: String,
//    val edited: String,
//    @SerializedName("eye_color")
//    val eyeColor: String,
//    val films: List<FilmResponse>,
//    val gender: String,
//    @SerializedName("hair_color")
//    val hairColor: String,
//    val height: String,
//    val homeworld: PlanetResponse,
//    val mass: String,
//    val name: String,
//    @SerializedName("skin_color")
//    val skinColor: String,
//    val species: List<SpeciesResponse>,
//    val starships: List<StarshipResponse>,
//    val url: String,
//    val vehicles: List<VehicleResponse>
//)