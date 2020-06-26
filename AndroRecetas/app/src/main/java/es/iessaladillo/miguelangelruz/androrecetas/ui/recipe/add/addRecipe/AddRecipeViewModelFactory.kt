package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addRecipe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao

class AddRecipeViewModelFactory(private val recipeDao: RecipeDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddRecipeViewModel::class.java)) {
            AddRecipeViewModel(
                recipeDao,
                this.application
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}