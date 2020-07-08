package es.miguelangelruz.starwarsuniverse.data.response


import com.google.gson.annotations.SerializedName

data class FilmResponse(
    val characters: List<CharacterResponse>,
    val created: String,
    val director: String,
    val edited: String,
    @SerializedName("episode_id")
    val episodeId: Int,
    @SerializedName("opening_crawl")
    val openingCrawl: String,
    val planets: List<PlanetResponse>,
    val producer: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val species: List<SpeciesResponse>,
    val starships: List<StarshipResponse>,
    val title: String,
    val url: String,
    val vehicles: List<VehicleResponse>
)