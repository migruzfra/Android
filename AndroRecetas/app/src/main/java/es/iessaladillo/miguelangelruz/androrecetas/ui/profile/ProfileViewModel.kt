package es.iessaladillo.miguelangelruz.androrecetas.ui.profile

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.User

class ProfileViewModel(private val userDao: UserDao, private val application: Application) :
    ViewModel() {

    val users: LiveData<List<User>> = queryAllUsers()
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    private val _currentUserId: MutableLiveData<Long> = MutableLiveData()
    val currentUserId: LiveData<Long>
        get() = _currentUserId

    private val _currentUser: MutableLiveData<User> = MutableLiveData()

    init {
        _currentUserId.value = settings.getLong("currentPlayer", -1)
    }

    private fun queryAllUsers(): LiveData<List<User>> = userDao.queryAllUsers()

    fun queryUser(idUser: Long) = userDao.queryUserFromId(idUser)

    fun setCurrentUserId(userId: Long) {
        _currentUserId.value = userId
    }

}