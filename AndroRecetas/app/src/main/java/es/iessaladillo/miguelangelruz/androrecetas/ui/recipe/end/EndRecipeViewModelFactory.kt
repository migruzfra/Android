package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.end

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao

class EndRecipeViewModelFactory(private val recipeDao: RecipeDao, private val userDao: UserDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EndRecipeViewModel::class.java)) {
            EndRecipeViewModel(recipeDao, userDao, this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}