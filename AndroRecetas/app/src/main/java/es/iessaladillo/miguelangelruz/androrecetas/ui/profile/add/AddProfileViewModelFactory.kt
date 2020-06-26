package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.add

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao

class AddProfileViewModelFactory(private val userDao: UserDao, private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddProfileViewModel::class.java)) {
            AddProfileViewModel(userDao, this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
