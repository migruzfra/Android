package es.miguelangelruz.starwarsuniverse.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import es.miguelangelruz.starwarsuniverse.data.response.*
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SwapiApiService {

    // characters
    @GET("people/{id}/")
    fun getCharacter(@Path("id") characterId: Int): Deferred<CharacterResponse>
    // all characters
    @GET("people/")
    fun getAllCharacters(@Query("page") page: Int? = null): Deferred<AllCharactersResponse> //La primera página, sin parámetro page
    @GET("people/")
    fun getAllCharacters(@Query("page") page: Int): Deferred<AllCharactersResponse> //sucesivas páginas, con parámetro page

    // films
    @GET("films/{id}/")
    fun getFilm(@Path("id") filmdId: Int): Deferred<FilmResponse>

    // planets
    @GET("planets/{id}/")
    fun getPlanet(@Path("id") planetId: Int): Deferred<PlanetResponse>

    // species
    @GET("species/{id}/")
    fun getSpecies(@Path("id") speciesId: Int): Deferred<SpeciesResponse>

    // starships
    @GET("starships/{id}/")
    fun getStarship(@Path("id") starshipId: Int): Deferred<StarshipResponse>

    // vehicles
    @GET("vehicles/{id}/")
    fun getVehicle(@Path("id") vehicleId: Int): Deferred<VehicleResponse>

    companion object {
        operator fun invoke(): SwapiApiService {
            //Si hubiera una apikey, se crea aquí un interceptor para añadirselo luego al okhttpclient

            val okHttpClient = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://swapi.dev/api/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) //Porque devolvemos Deferred
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SwapiApiService::class.java)
        }
    }
}