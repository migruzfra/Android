package es.miguelangelruz.starwarsuniverse.ui.characters.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.miguelangelruz.starwarsuniverse.data.SwapiApiService
import es.miguelangelruz.starwarsuniverse.data.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val apiService = SwapiApiService()

    private val _character: MutableLiveData<CharacterResponse> = MutableLiveData()
    val character: LiveData<CharacterResponse>
        get() = _character

    private val _characterIsCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val characterIsCompleted: LiveData<Boolean>
        get() = _characterIsCompleted

    lateinit var parentJob: Job

    private fun createCharacter(cr: CharacterResponse, parentJobCoroutineScope: CoroutineScope) {
        var planet: PlanetResponse =
            PlanetResponse("", "", "", "", listOf(), "", "", "", "", listOf(), "", "", "", "")
        val films: MutableList<FilmResponse> = mutableListOf()
        val species: MutableList<SpeciesResponse> = mutableListOf()
        val starships: MutableList<StarshipResponse> = mutableListOf()
        val vehicles: MutableList<VehicleResponse> = mutableListOf()

        parentJobCoroutineScope.launch {

            launch {
                cr.species.forEach {
                    species.add(querySpecies(getIdFromUrl(it)).await())
                }
            }
            launch {
                cr.starships.forEach {
                    starships.add(queryStarship(getIdFromUrl(it)).await())
                }
            }
            launch {
                cr.vehicles.forEach {
                    vehicles.add(queryVehicle(getIdFromUrl(it)).await())
                }
            }

        }


    }

    fun getCharacter(characterId: Long) {
        var characterResponse: CharacterResponse
        _characterIsCompleted.value = false
        parentJob = CoroutineScope(Dispatchers.Main).launch {
            launch {
                characterResponse = queryCharacter(characterId).await()

                launch {
                    characterResponse.films.map { film ->
                        queryFilm(getIdFromUrl(film)).await().title
                    }
                    println(characterResponse.films)
                }
                launch {
                    characterResponse.homeworld =
                        queryPlanet(getIdFromUrl(characterResponse.homeworld)).await().name
                }
            }.join()
        }

        _characterIsCompleted.value = true
        _character.value = characterResponse.copy()


        //Si quisiera lanzar otro hilo, no tengo más que hacer otro launch aquí
        //Para que, en caso de cancelar el parentJob, se cancelen todos los launch hijos
    }


    private fun getIdFromUrl(url: String): Long {
        val regex = Regex("\\d+")
        val match = regex.find(url)
        return match?.groups?.first()?.value!!.toLong()
    }

    fun queryCharacter(characterId: Long) = apiService.queryCharacter(characterId)
    fun queryPlanet(planetId: Long) = apiService.queryPlanet(planetId)
    fun queryFilm(filmId: Long) = apiService.queryFilm(filmId)
    fun querySpecies(speciesId: Long) = apiService.querySpecies(speciesId)
    fun queryStarship(starshipId: Long) = apiService.queryStarship(starshipId)
    fun queryVehicle(vehicleId: Long) = apiService.queryVehicle(vehicleId)
}
