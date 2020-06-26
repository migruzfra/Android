package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.overview

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps

class OverviewViewModel(private val recipeDao: RecipeDao, private val userDao: UserDao, private val application: Application) : ViewModel()  {

    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun getRecipe(codeRecipe: Long) = recipeDao.queryRecipe(codeRecipe)

    fun getUsernameFromId(idUser: Long) = userDao.queryUsernameFromId(idUser)

    fun getUserImgFromId(idUser: Long) = userDao.getUserImgFromId(idUser)

    fun ingredientsToShoppingList(listIngredients : String) {
        val ing : String = userDao.queryShoppingList(settings.getLong("currentPlayer", -1))
        userDao.updateShoppingList(ing + ", " + listIngredients, settings.getLong("currentPlayer", -1))
    }

}