package es.iessaladillo.miguelangelruz.androrecetas.ui.search.ranking

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps

class RankingViewModel(val recipeDao: RecipeDao, private val application: Application): ViewModel() {

    private var _listRecipes : MutableList<RecipeWithSteps> = ArrayList()
    val listRecipes: List<RecipeWithSteps> get()=_listRecipes

    init {
        queryBestRecipes()
    }

    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun queryBestRecipes() {
        _listRecipes =  recipeDao.queryBestRecipes().toMutableList()
    }
}