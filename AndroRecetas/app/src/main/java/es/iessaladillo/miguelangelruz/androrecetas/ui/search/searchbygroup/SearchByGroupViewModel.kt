package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchbygroup

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps

class SearchByGroupViewModel(val recipeDao: RecipeDao, val application: Application) : ViewModel() {
    init {
        getRecipes()
    }

    var recipes: List<RecipeWithSteps> = listOf()


    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun getRecipes() {
        recipes = recipeDao.queryAllRecipesWithSteps()
    }
}