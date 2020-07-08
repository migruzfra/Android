package es.miguelangelruz.starwarsuniverse.data.response

data class AllCharactersResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<CharacterResponse>
)