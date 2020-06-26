package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchmenu

import android.app.Application
import androidx.lifecycle.ViewModel
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao

class SearchMenuViewModel(val recipeDao: RecipeDao, val application: Application) : ViewModel() {

    fun queryListIdRecipes(): List<Long> {
        return recipeDao.queryListIdRecipes()
    }

    fun deleteRecipesWithoutSteps() {
        recipeDao.deleteRecipesWithoutSteps()
    }

    fun deleteJunkRecipes() {
        recipeDao.deleteJunkRecipes()
    }
}