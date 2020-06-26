package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.shoppingList

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceManager.*
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class ShoppingListViewModel(val userDao: UserDao, private val application: Application) :
    ViewModel() {

    private var _listItems : MutableList<String> = ArrayList()
    val listItems: List<String> get()=_listItems
    //Acceso a shared preferences
    val settings: SharedPreferences by lazy {
        getDefaultSharedPreferences(application)
    }
    init {
        queryShoppingList(settings.getLong("currentPlayer", 1L))
    }

    fun addItem(item: String) {
        _listItems.add(item)
    }

    fun updateShoppingList(userId: Long) {
        userDao.updateShoppingList(_listItems.joinToString(), userId)
    }

    fun deleteItem(position: Int) {
        _listItems.removeAt(position)
    }

    fun queryShoppingList(userId: Long) {
        _listItems = userDao.queryShoppingList(userId).split(",").map { it.trim() }.distinct().sortedWith(
                Comparator { o1 : String, o2 : String -> o1.toLowerCase().replace(Regex("[^a-z]"), "").compareTo(o2.toLowerCase().replace(Regex("[^a-z]"), "")) }
                ).toMutableList()
        _listItems.remove("")
    }

    fun clearList() {
        _listItems.clear()
    }

}