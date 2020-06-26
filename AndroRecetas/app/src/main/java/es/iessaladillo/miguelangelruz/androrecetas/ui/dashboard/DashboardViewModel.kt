package es.iessaladillo.miguelangelruz.androrecetas.ui.dashboard

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps
import kotlin.properties.Delegates

class DashboardViewModel(val recipeDao: RecipeDao, val userDao: UserDao, val application: Application) : ViewModel() {

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    private val _currentUserId: MutableLiveData<Long> = MutableLiveData()
    val currentUserId: LiveData<Long>
        get() = _currentUserId

    init {
        getRecipes()
        _currentUserId.value = settings.getLong("currentPlayer", -1)
    }

    var recipes: List<RecipeWithSteps> = listOf()

    fun getRecipes() {
        recipes = recipeDao.queryAllRecipesWithSteps()
    }

    fun getUsernameFromId(idUser: Long) = userDao.queryUsernameFromId(idUser)
    fun getUserImgFromId(idUser: Long) = userDao.queryUserImgFromId(idUser)





}