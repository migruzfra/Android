package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.shoppingList

import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShoppingListViewModelFactory(private val userDao: UserDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java)) {
            ShoppingListViewModel(
                userDao,
                this.application
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}