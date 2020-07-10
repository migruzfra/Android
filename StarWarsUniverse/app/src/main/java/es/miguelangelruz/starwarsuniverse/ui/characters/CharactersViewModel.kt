package es.miguelangelruz.starwarsuniverse.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.miguelangelruz.starwarsuniverse.data.SwapiApiService
import es.miguelangelruz.starwarsuniverse.data.response.CharacterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private val apiService = SwapiApiService()

    private val _listCharacters: MutableLiveData<List<CharacterResponse>> = MutableLiveData(arrayListOf())
    val listCharacters: LiveData<List<CharacterResponse>>
        get() = _listCharacters

    private val _listIsCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val listIsCompleted: LiveData<Boolean>
        get() = _listIsCompleted

    lateinit var parentJob: Job


    fun getCharacters() {
        _listIsCompleted.value = false
        parentJob = CoroutineScope(Dispatchers.Main).launch {
            launch {
                var allCharactersResponse = queryAllCharacters().await()
                val charactersQueriedSoFar: MutableList<CharacterResponse> =
                    allCharactersResponse.results.toMutableList()
                _listCharacters.value = charactersQueriedSoFar

                while (allCharactersResponse.next != null) {
                    val nextPage = allCharactersResponse.next!!.takeLast(1).toInt()
                    allCharactersResponse = queryAllCharacters(nextPage).await()
                    charactersQueriedSoFar.addAll(allCharactersResponse.results)
                    _listCharacters.value = charactersQueriedSoFar.toList()
                }
                _listIsCompleted.value = true
            }
            //Si quisiera lanzar otro hilo, no tengo más que hacer otro launch aquí
            //En caso de cancelar el parentJob, se cancelan todos los launch hijos
        }
    }

    fun queryAllCharacters() = apiService.queryAllCharacters()
    fun queryAllCharacters(page: Int) = apiService.queryAllCharacters(page)
}