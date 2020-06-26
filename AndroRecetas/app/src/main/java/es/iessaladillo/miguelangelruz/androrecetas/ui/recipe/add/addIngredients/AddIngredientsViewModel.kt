package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addIngredients

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps

class AddIngredientsViewModel(val recipeDao: RecipeDao, private val application: Application): ViewModel() {

    private val _listIngredients : MutableList<String> = ArrayList()
    val listIngredients: List<String> get()=_listIngredients

    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun insertIngredients(codeRecipe: Long) = recipeDao.insertIngredients(_listIngredients.joinToString(), codeRecipe)

    fun addIngredient(ingredient: String) {
        _listIngredients.add(ingredient)
    }

    fun deleteIngredient(position: Int) {
        _listIngredients.removeAt(position)
    }
}