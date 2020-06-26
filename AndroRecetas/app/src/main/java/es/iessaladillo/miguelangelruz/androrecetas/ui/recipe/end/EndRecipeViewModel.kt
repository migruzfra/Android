package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.end

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao

class EndRecipeViewModel(private val recipeDao: RecipeDao, private val userDao: UserDao, private val application: Application) : ViewModel()  {

    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun getRecipe(codeRecipe: Long) = recipeDao.queryRecipe(codeRecipe)
    fun getRecipeName(codeRecipe: Long) = recipeDao.queryRecipe(codeRecipe).recipe.nameRecipe

    fun getUsernameFromId(idUser: Long) = userDao.queryUsernameFromId(idUser)

    fun updateRatingRecipe(totalPoints: Float, totalVoters: Int, codeRecipe: Long) = recipeDao.updateRatingRecipe(totalPoints, totalVoters, codeRecipe)
    fun updateUserRecipesRated(recipesRated: String) = userDao.updateRecipesRated(recipesRated, settings.getLong("currentPlayer", 1))

    fun getCurrentUser() = userDao.queryUserFromId(settings.getLong("currentPlayer", 1))


}