package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addSteps

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps

class AddStepsViewModel(val recipeDao: RecipeDao, private val application: Application): ViewModel() {

    private val _listSteps : MutableList<Step> = ArrayList()
    val listSteps: List<Step> get()=_listSteps

    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun insertSteps() = recipeDao.insertSteps(_listSteps)

    fun addStep(step: Step) {
        _listSteps.add(step)
    }

    fun queryAllRecipes() : List<RecipeWithSteps> {
        return recipeDao.queryAllRecipesWithSteps()
    }

    fun deleteRecipe(codeRecipe : Long) {
        recipeDao.deleteRecipe(codeRecipe)
    }

    fun deleteStep(position: Int) {
        _listSteps.removeAt(position)
    }
}