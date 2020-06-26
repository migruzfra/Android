package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchbyname

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps

class SearchByNameViewModel(val recipeDao: RecipeDao, val application: Application) : ViewModel() {


    var recipesByName: List<RecipeWithSteps> = recipeDao.queryRecipeByNamee("%" + "" + "%")

    fun getRecipesByName(nameRecipe: String) : List<RecipeWithSteps> {
        recipesByName = recipeDao.queryRecipeByNamee(nameRecipe)
        return recipesByName
    }

}