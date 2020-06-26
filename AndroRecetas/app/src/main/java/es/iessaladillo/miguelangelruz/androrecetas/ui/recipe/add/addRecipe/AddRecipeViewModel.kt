package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addRecipe

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.Event
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe

class AddRecipeViewModel(private val recipeDao: RecipeDao, private val application: Application): ViewModel() {

    private val _message : MutableLiveData<Event<String>> = MutableLiveData()
    val message : LiveData<Event<String>> get()=_message

    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun getCurrentUser() = settings.getLong("currentPlayer", 1)

    fun insertRecipe(recipe: Recipe) = recipeDao.insertRecipe(recipe)

    fun errorValidationMessage() {
        _message.postValue(Event(application.resources.getString(R.string.error_validation_message_addrecipe)))
    }
}